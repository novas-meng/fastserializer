package novasIo;

import serializer.objectSerializer;
import type.IntArray;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by novas on 16/2/28.
 */
public class Input
{
    int capcity=4096;
    byte[] buffer=new byte[capcity];
    int position=0;
    FileInputStream fileInputStream;
    public Input(String path)
    {
        try
        {
          fileInputStream=new FileInputStream(path);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //allocate byte buffer dynamic.according to the bytelength;
    public void require(int bytelength)
    {
        if(position+bytelength>=capcity)
        {
            try
            {
                byte[] temp=new byte[capcity];
                int length=capcity-position;
                System.arraycopy(buffer,position,temp,0,length);
                fileInputStream.read(temp,length,temp.length-length);
                buffer=temp;
                position=0;
            }
            catch (Exception e)
            {
                System.out.println(position);
                System.out.println(bytelength);
              //  System.out.println(b)
                e.printStackTrace();
            }
        }
    }
    public String readFieldName()
    {
        require(1);
        int length=buffer[position++];
        require(length);
       // System.out.println("length="+length);
        byte[] strsbytes=new byte[length];
        for(int i=0;i<length;i++)
        {
            strsbytes[i]=buffer[position++];
        }
      //  System.out.println(strsbytes[0]);
        return new String(strsbytes);
    }
    public String readObjectName()
    {
        return readFieldName();
    }
    public int isBasicType()
    {
        require(1);
       return buffer[position++];
    }
    public Type readBasicType()
    {
        require(1);
        return BasicType.getBasicType(buffer[position++]);
    }
    public Class readClass()
    {
        require(1);
        int length=buffer[position++];
        require(length);
        byte[] temp=new byte[length];
        System.arraycopy(buffer,position,temp,0,temp.length);
        position=position+temp.length;
        Class cls=null;
        try
        {
          //  System.out.println(new String(temp));
            cls=Class.forName(new String(temp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cls;
    }
    public String readValueString()
    {
        int length=readInt();
        require(length);
        byte[] bytes=new byte[length];
        for(int i=0;i<length;i++)
        {
            bytes[i]=buffer[position++];
        }
        return new String(bytes);
    }
    public Object readValue(Type type)
    {
       if(type==Integer.TYPE)
       {
          return readInt();
       }
       else if(type==Double.TYPE)
       {
           return readDouble();
       }
       else if(type==String.class)
       {
           return readValueString();
       }
       else if(type== IntArray.getType())
       {
           return readIntArray();
       }
       else if(type== HashMap.class)
       {
           return readMap();
       }
        return readInt();
    }
    public Map readMap()
    {
        int mapsize=readInt();
        Map map=new HashMap();
      //  System.out.println("mapsize="+mapsize);
        Object key=null;
        Object value=null;
        for(int i=0;i<mapsize;i++)
        {

            int keyPtr=isBasicType();
            if(keyPtr==1)
            {
              //  System.out.println("是基本类�?");
                Type type=readBasicType();
                if(type==String.class)
                {
                  //  System.out.println("keys");
                    key=readFieldName();
                  //  System.out.println("key="+key.toString());
                }
            }
            int valuePtr=isBasicType();
            if(valuePtr==1)
            {
              //  System.out.println("是基本类�?");
                Type type=readBasicType();
                if(type==IntArray.getType())
                {
                   // System.out.println("values");
                    value=readIntArray();
                    int[] array=(int[])value;
                 //   for(int j=0;j<array.length;j++)
                 //   {
                      //  System.out.println(array[j]);
                   // }
                 //   System.out.println("key="+value.toString());
                }
            }
            map.put(key,value);
        }
        return map;
    }
    public int[] readIntArray()
    {
        int length=readInt();
    //    System.out.println("array length="+length);
        int[] array=new int[length];
        for(int i=0;i<length;i++)
        {
            array[i]=readInt();
           // System.out.println(array[i]);
        }
        return array;
    }
    public double readDouble()
    {
        long m=readLong();
        return Double.longBitsToDouble(m);
    }
    public long readLong()
    {
        require(8);
        long s = 0;
        long s0 = (buffer[position++] & 0xff);// �??低位
        long s1 = (buffer[position++] & 0xff);
        long s2 = (buffer[position++] & 0xff);
        long s3 = (buffer[position++] & 0xff);
        long s4 = (buffer[position++] & 0xff);// �??低位
        long s5 = (buffer[position++] & 0xff);
        long s6 = (buffer[position++] & 0xff);
        long s7 = (buffer[position++] & 0xff);
        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 32;
        s5 <<= 40;
        s6 <<= 48;
        s7 <<= 56;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;

    }
    public int readInt() {
         require(4);
         return (buffer[position++]&255)+((buffer[position++]&255)<<8)+((buffer[position++]&255)<<16)+((buffer[position++]&255)<<24);
        // return (buffer[position++]&255)+((buffer[position++]&255)<<8);
/*
        require(1);
        int length = buffer[position++];
        if (length == 1) {
            require(1);
            return (buffer[position++] & 255);
        } else if (length == 2) {
            require(2);
            return (buffer[position++] & 255) + ((buffer[position++] & 255) << 8);
        } else if (length == 3) {
            require(3);
            return (buffer[position++] & 255) + ((buffer[position++] & 255) << 8) + ((buffer[position++] & 255) << 16);
        } else {
            require(4);
            return (buffer[position++] & 255) + ((buffer[position++] & 255) << 8) + ((buffer[position++] & 255) << 16) + ((buffer[position++] & 255) << 24);
        }
*/
    }
    public Object readObject()
    {
        try
        {
            fileInputStream.read(buffer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
      //  System.out.println(buffer[0]);
        Object object= objectSerializer.readObject(this);
        return object;
    }
}
