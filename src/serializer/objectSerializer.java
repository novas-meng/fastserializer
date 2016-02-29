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
        if(cls!=null)
        {
            try
            {
                Object object=cls.newInstance();
                Field[] fields=cls.getDeclaredFields();
                int ptr=input.isBasicType();
                while (ptr>0)
                {
                    //ptr==1表示是基本类型
                    if(ptr==1)
                    {
                       // System.out.println("基本类型" + input.isBasicType());
                        Type type=input.readBasicType();
                        String m=input.readFieldName();
                        Object value=input.readValue(type);
                        System.out.println("m=" + m);
                        Field field=cls.getDeclaredField(m);
                        field.setAccessible(true);
                        field.set(object,value);
                        if(type==Integer.TYPE)
                        {
                            System.out.println("integer");
                        }
                        if(type==Double.TYPE)
                        {
                            System.out.println("zhengxing");
                        }
                    }
                    ptr=input.isBasicType();
                }
                return object;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
                try
                {
                    if(type==Integer.TYPE)
                    {
                        System.out.println(fields[i].getName());
//                        System.out.println(fields[i].getInt(object));
                        fields[i].setAccessible(true);
                        intSerializer.writeInt(output,fields[i].getName(),fields[i].getInt(object));
                    }
                    else if(type==Double.TYPE)
                    {
                        fields[i].setAccessible(true);
                        doubleSerializer.writeDouble(output, fields[i].getName(), fields[i].getDouble(object));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
