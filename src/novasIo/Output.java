package novasIo;

import serializer.objectSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by novas on 16/2/28.
 */
public class Output
{
    int capcity=40960;
    byte[] buffer=new byte[capcity];
    int position=0;
    FileOutputStream fileOutputStream;
    public Output(String path)
    {
        this(path, 40960);
    }
    public void require(int bytecount)
    {
        if(position+bytecount>=capcity)
        {
            try
            {
                fileOutputStream.write(buffer,0,position);
                position=0;
                buffer=new byte[capcity];
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public Output(String path,int capcity)
    {
        this.capcity=capcity;
        buffer=new byte[capcity];
        try {
            fileOutputStream=new FileOutputStream(path);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void writeBasicFlag(Type type)
    {
        require(2);
        buffer[position++]=1;
       // if(type==Integer.TYPE)
       // {
            buffer[position++]=(byte)BasicType.isBasicType(type);
       // }
    }
    public void writeObjectType()
    {
        require(1);
        buffer[position++]=2;
    }
    //write the field name ,the max length is 127
    public void writeString(String m)
    {
        byte[] chars=m.getBytes();
        require(1+chars.length);
        writeByte(m.length());
        for(int i=0;i<chars.length;i++)
        {
            buffer[position++]=chars[i];
        }
    }
    //write the string ,but the length may be above 127,so choose int;
    public void writeValueString(String m)
    {
        byte[] chars=m.getBytes();
        require(1+chars.length);
        writeInt(m.length());
        for(int i=0;i<chars.length;i++)
        {
            buffer[position++]=chars[i];
        }
    }
    public void writeByte(int length)
    {
        require(1);
        buffer[position++]=(byte)length;
    }
    public void writeDouble(double m)
    {
        long n=Double.doubleToLongBits(m);
        writeLong(n);
    }
    public void writeLong(long m)
    {
        require(8);
        buffer[position++]=(byte)(m&255);
        buffer[position++]=(byte)((m>>8)&255);
        buffer[position++]=(byte)((m>>16)&255);
        buffer[position++]=(byte)((m>>24)&255);
        buffer[position++]=(byte)((m>>32)&255);
        buffer[position++]=(byte)((m>>40)&255);
        buffer[position++]=(byte)((m>>48)&255);
        buffer[position++]=(byte)((m>>56)&255);

    }
    public void writeInt(int m)
    {
/*
        if(m>>8==0)
        {
              require(2);
              buffer[position++]=1;
              buffer[position++]=(byte)(m&255);
              //buffer[position++]=(byte)((m>>8)&255);
            //  buffer[position++]=(byte)((m>>16)&255);
            // buffer[position++]=(byte)((m>>24)&255);
        }
        else if(m>>16==0)
        {
            require(3);
            buffer[position++]=2;
            buffer[position++]=(byte)(m&255);
            buffer[position++]=(byte)((m>>8)&255);
            //  buffer[position++]=(byte)((m>>16)&255);
            // buffer[position++]=(byte)((m>>24)&255);
        }
        else if(m>>24==0)
        {
            require(4);
            buffer[position++]=3;
            buffer[position++]=(byte)(m&255);
            buffer[position++]=(byte)((m>>8)&255);
            buffer[position++]=(byte)((m>>16)&255);
            // buffer[position++]=(byte)((m>>24)&255);
        }
        else
        {
            require(5);
            buffer[position++]=4;
            buffer[position++]=(byte)(m&255);
            buffer[position++]=(byte)((m>>8)&255);
            buffer[position++]=(byte)((m>>16)&255);
            buffer[position++]=(byte)((m>>24)&255);
        }
        */

        require(4);
        buffer[position++]=(byte)(m&0xff);
        buffer[position++]=(byte)((m>>8)&0xff);
        buffer[position++]=(byte)((m>>16)&0xff);
        buffer[position++]=(byte)((m>>24)&0xff);

    }
    public void writeArrayLength(int length)
    {
        require(4);
        this.writeInt(length);
    }
    public void writeObject(Object object)
    {
       // long m=System.currentTimeMillis();
        objectSerializer.writeObjectClass(this,"src", object);
        try {
            fileOutputStream.write(buffer,0,position);
            fileOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
      //  long n=System.currentTimeMillis();
     //   System.out.println("write time="+(n-m));
    }
}
