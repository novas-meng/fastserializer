import novasIo.BasicType;
import novasIo.Input;
import novasIo.Output;
import novasIo.novas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by novas on 16/2/28.
 */
public class demo {
    public static void main(String[] args)throws Exception
    {
        father father=new father();
        novas novas=new novas();
        Output output=new Output("file.bin");
        output.writeObject(father);
        Input input=new Input("file.bin");
        input.readObject();
        /*
        father father=new father();

     //   father.init();
        father.a=6;

        int m=2748;
        byte[] bytes=new byte[4];
        writeInt(bytes, m, 0);
        System.out.println(Byte.MAX_VALUE);
      //  write(father);
       // byte[] bytes1="a".getBytes();
       // System.out.println(bytes1[0]+"  "+bytes1.length);
        FileInputStream fis=new FileInputStream("bin");
        byte[] bytes1=new byte[4096];
        fis.read(bytes1);
        //read(bytes1);
        father father1=(father)read(bytes1);
        System.out.println(father1.a);
        */
    }
    public static Object read(byte[] bytes)throws Exception
    {
        int length=bytes[0];
        int i=1;
        byte[] classname=new byte[length];
        System.arraycopy(bytes, i, classname, 0, classname.length);
        String ClsName=new String(classname);
        System.out.println(ClsName);
        Class c=Class.forName(ClsName);
        i=i+classname.length;
        if(c!=null)
        {
            //System.out.println("null");
            Object obj=c.newInstance();
            Field[] fields=c.getDeclaredFields();
            System.out.println(fields.length+"  length");
            for(int j=0;j<fields.length;j++)
            {
              //  System.out.println(bytes[i]);
              //  System.out.println(bytes[i+1]);
              //  System.out.println(bytes[i+2]);
              //  System.out.println(bytes[i+3]);

                fields[j].setInt(obj,bytes[i+4]);
            }

            return obj;
        }
        return null;
    }
    public static byte[] write(Object object)throws Exception
    {
        byte[] bytes=new byte[4096];
        String name=object.getClass().getName();
        byte[] namebytes=name.getBytes();
        bytes[0]=6;
        int i=1;
        System.arraycopy(namebytes, 0, bytes, i, namebytes.length);
        i=i+namebytes.length;
        Class c=object.getClass();
        Field[] fields=c.getDeclaredFields();
        for(int j=0;j<fields.length;j++)
        {
            Field field=fields[j];
            Type type=field.getGenericType();
            System.out.println(type.toString());
            if(type==Integer.TYPE){
               int m= field.getInt(object);
                //1表示是基本类型
               bytes[i]=1;
                //1表示是int类型
                bytes[i+1]=1;
                //1表示名称长度1
                bytes[i+2]=1;
                bytes[i+3]=field.getName().getBytes()[0];
                bytes[i+4]=(byte)field.getInt(object);
            }
        }
        i=i+5;
        FileOutputStream fileOutputStream=new FileOutputStream("bin");
        fileOutputStream.write(bytes,0,i);
        fileOutputStream.close();
        return bytes;
    }
    public static int bytestoint(byte[] bytes)
    {
        int target=(bytes[0]&255)+((bytes[1]&255)<<8)+((bytes[2]&255)<<16)+((bytes[3]&255)<<24);
        return target;
    }
    public static void writeInt(byte[] bytes,int m,int loc)
    {/*
        bytes[loc]=(byte)(127&(m));
        bytes[loc+1]=(byte)(127&(m>>8));
        bytes[loc+2]=(byte)(127&(m>>16));
        bytes[loc+3]=(byte)(127&(m>>24));
        System.out.println( bytes[loc]);
        System.out.println( bytes[loc+1]);

        System.out.println( bytes[loc+2]);
        System.out.println( bytes[loc+3]);
        */
       // byte[] bytes1=new byte[4];
       // Integer a=m;
       // Integer.toBinaryString(m).getBy;
        bytes[loc++]=(byte)(m&255);
        bytes[loc++]=(byte)((m>>8)&255);
        bytes[loc++]=(byte)((m>>16)&255);
        bytes[loc++]=(byte)((m>>24)&255);
    }
}
