package novasIo;

import serializer.objectSerializer;

import java.io.FileInputStream;

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
        return null;
    }
}
