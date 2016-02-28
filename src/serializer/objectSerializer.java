package serializer;

import novasIo.BasicType;
import novasIo.Input;
import novasIo.Output;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by novas on 16/2/28.
 */
public class objectSerializer
{
    //根据字节数组和反射生成字object信息
    public static Object readObject(Input input)
    {
        Class cls=input.readClass();
        System.out.println("read="+cls.getName());
        return null;
    }
    //根据反射进行写入object信息
    public static void writeObject(Output output,Object object)
    {
        Class cls=object.getClass();
        System.out.println(cls.getName());
        classSerializer.writeClass(output,cls);
        Field[] fields=cls.getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            Type type=fields[i].getGenericType();
            int index= BasicType.isBasicType(type);
            if(index!=-1)
            {
                if(type==Integer.TYPE)
                {
                    try
                    {
                        System.out.println(fields[i].getName());
//                        System.out.println(fields[i].getInt(object));
                        fields[i].setAccessible(true);
                        intSerializer.writeInt(output,fields[i].getName(),fields[i].getInt(object));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
