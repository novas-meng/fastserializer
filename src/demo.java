
import novasIo.Input;
import novasIo.Output;
import novasIo.novas;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by novas on 16/2/28.
 */
public class demo {
    public static void main(String[] args)throws Exception
    {
        /*
        father f=new father();
        f.change();
        Field[] fields=f.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            System.out.println(fields[i].getGenericType().getTypeName());

           if(fields[i].getGenericType()==student.class)
           {
               Class subclass=Class.forName(fields[i].getGenericType().getTypeName());
               Object obj=fields[i].get(f);
               Field[] sub=subclass.getDeclaredFields();
               System.out.println("baohanduixiang");
               for(int j=0;j<sub.length;j++)
               System.out.println("class=" + sub[j].getName());
              // Field[] classfields=fields[i].getGenericType();
           }

        }
        */

        long m=Double.doubleToLongBits(100.7895456);
        System.out.println( Double.longBitsToDouble(m));
        father father=new father();
        father.a=89;
        father.c=98888.7;
        father.intarray[0]=8;
        father.change();

        long time=System.currentTimeMillis();

        novas novas=new novas();
       Output output=new Output("file.bin");
        output.writeObject(father);
        Input input=new Input("file.bin");
        father=(father)input.readObject();
        long time1=System.currentTimeMillis();

        System.out.println("time="+(time1-time));


        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("father.bin"));
        oos.writeObject(father);
        oos.close();
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("father.bin"));
        father=(father)ois.readObject();

        long time2=System.currentTimeMillis();
        System.out.println(father.a);
        System.out.println(father.demo);
        System.out.println(father.c);
        System.out.println(father.intarray[4]);
        System.out.println(father.st.c);
      //  System.out.println(father.map.get("7")[8]);
        System.out.println("time="+(time2-time1));
      //  int[] array=new int[2];
      //  array[0]=0;
      //  array[1]=1;
      //  int[] array1=new int[2];
      //  array1[0]=0;
     //   array1[1]=1;
     //   System.out.println(array==array1);
        //father f=new father();
       // Field[] fields=f.getClass().getDeclaredFields();
       // for(int i=0;i<fields.length;i++)
       // {
       //     System.out.println(fields[i].getGenericType());
       // }
        /*
        child c=new child();
        f.child=c;
        c.father=f;
        f.c=200;
        System.out.println(f.hashCode());
        System.out.println(c.father.hashCode());
        */
    }
}
