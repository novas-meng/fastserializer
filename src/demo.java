
import novasIo.Input;
import novasIo.Output;
import novasIo.novas;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

        long m=Double.doubleToLongBits(100.7895456 );
        System.out.println( Double.longBitsToDouble(m));
        long time=System.currentTimeMillis();
        father father=new father();
        father.a=89;
        father.c=98888.7;
        father.array[0]=8;
        father.change();

        novas novas=new novas();

        Output output=new Output("file.bin");
        output.writeObject(father);
        Input input=new Input("file.bin");
        father=(father)input.readObject();
        time=System.currentTimeMillis()-time;
        System.out.println(father.a);
        System.out.println(father.demo);
        System.out.println(father.c);
        System.out.println(father.array[4896]);
        System.out.println(father.st.c);
        System.out.println("time="+time);
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
