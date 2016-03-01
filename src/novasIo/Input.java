package novasIo;

import serializer.objectSerializer;

import java.io.FileInputStream;
import java.lang.reflect.Type;

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
    public String readFieldName()
    {
        int length=buffer[position++];
        byte[] strsbytes=new byte[length];
        for(int i=0;i<length;i++)
        {
            strsbytes[i]=buffer[position++];
        }
        return new String(strsbytes);
    }
    public String readObjectName()
    {
        return readFieldName();
    }
    public int isBasicType()
    {
       return buffer[position++];
    }
    public Type readBasicType()
    {
        return BasicType.getBasicType(buffer[position++]);
    }
    public Class readClass()
    {
        int length=buffer[position++];
        byte[] temp=new byte[length];
        System.arraycopy(buffer,position,temp,0,temp.length);
        position=position+temp.length;
        Class cls=null;
        try
        {
            System.out.println(new String(temp));
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
        return readInt();
    }
    public double readDouble()
    {
        long m=readLong();
        return Double.longBitsToDouble(m);
    }
    public long readLong()
    {
        long s = 0;
        long s0 = (buffer[position++] & 0xff);// �?低位
        long s1 = (buffer[position++] & 0xff);
        long s2 = (buffer[position++] & 0xff);
        long s3 = (buffer[position++] & 0xff);
        long s4 = (buffer[position++] & 0xff);// �?低位
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
    public int readInt()
    {
        return (buffer[position++]&255)+((buffer[position++]&255)<<8)+((buffer[position++]&255)<<16)+((buffer[position++]&255)<<24);
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
        System.out.println(buffer[0]);
        Object object= objectSerializer.readObject(this);
        return object;
    }
}
