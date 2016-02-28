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
        if(type==Integer.TYPE)
        {
            buffer[position++]=1;
        }
    }
    public void writeString(String m)
    {
        writeInt(m.length());
        byte[] chars=m.getBytes();
        for(int i=0;i<chars.length;i++)
        {
            buffer[position++]=chars[i];
        }
    }
    public void writeInt(int m)
    {
        buffer[position++]=(byte)(m&255);
        buffer[position++]=(byte)((m>>8)&255);
        buffer[position++]=(byte)((m>>16)&255);
        buffer[position++]=(byte)((m>>24)&255);
    }
    public void writeObject(Object object)
    {
        objectSerializer.writeObject(this, object);
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
