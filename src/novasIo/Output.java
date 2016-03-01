package novasIo;

import serializer.objectSerializer;

import java.io.FileOutputStream;
import java.lang.reflect.Type;

/**
 * Created by novas on 16/2/28.
 */
public class Output
{
    int capcity=4096;
    byte[] buffer=new byte[capcity];
    int position=0;
    FileOutputStream fileOutputStream;
    public Output(String path)
    {
        this(path, 4096);
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
        buffer[position++]=1;
       // if(type==Integer.TYPE)
       // {
            buffer[position++]=(byte)BasicType.isBasicType(type);
       // }
    }
    public void writeObjectType()
    {
        buffer[position++]=2;
    }
    //这个函数用来写入变量的名称，长度不超过127
    public void writeString(String m)
    {
        writeByte(m.length());
        byte[] chars=m.getBytes();
        for(int i=0;i<chars.length;i++)
        {
            buffer[position++]=chars[i];
        }
    }
    //这个函数写入sring的值
    public void writeValueString(String m)
    {
        writeInt(m.length());
        byte[] chars=m.getBytes();
        for(int i=0;i<chars.length;i++)
        {
            buffer[position++]=chars[i];
        }
    }
    public void writeByte(int length)
    {
        buffer[position++]=(byte)length;
    }
    public void writeDouble(double m)
    {
        long n=Double.doubleToLongBits(m);
        writeLong(n);
    }
    public void writeLong(long m)
    {
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
        buffer[position++]=(byte)(m&255);
        buffer[position++]=(byte)((m>>8)&255);
        buffer[position++]=(byte)((m>>16)&255);
        buffer[position++]=(byte)((m>>24)&255);
    }
    public void writeArrayLength(int length)
    {
        this.writeInt(length);
    }
    public void writeObject(Object object)
    {
        objectSerializer.writeObjectClass(this,"src", object);
        try {
            fileOutputStream.write(buffer,0,position);
            fileOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
