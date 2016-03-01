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
       System.out.println("basic type" + input.isBasicType());
        Class cls=input.readClass();
        String name=input.readObjectName();
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
                    else if(ptr==2)
                    {
                        System.out.println("not basic type");
                      //  objectSerializer.readObject(input);
                        Class subcls=input.readClass();
                        System.out.println("class="+subcls.getName());
                        String fieldname=input.readFieldName();
                        System.out.println("name="+fieldname);
                        Object subobj=subcls.newInstance();
                        input.isBasicType();
                        Type type=input.readBasicType();
                        String m=input.readFieldName();
                        Object value=input.readValue(type);
                        System.out.println("m=" + (Integer)value);
                        Field field=subcls.getDeclaredField(m);
                        field.setAccessible(true);
                        field.set(subobj, value);
                        Field field1=cls.getDeclaredField(fieldname);
                        field1.setAccessible(true);
                       field1.set(object, subobj);
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
    public static void writeObject(Output output,String name,Object object)
    {
        Class cls=object.getClass();

        System.out.println("current "+cls.getName());
        classSerializer.writeClass(output, cls);
        classSerializer.writeObjectName(output, name);
        Field[] fields=cls.getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            System.out.println("field="+fields[i].getName());
            Type type=fields[i].getGenericType();
            int index= BasicType.isBasicType(type);
            try
            {
                //处理类型是基本类型
                if(index!=-1)
                {
                    fields[i].setAccessible(true);
                    if(type==Integer.TYPE)
                    {
                        System.out.println(fields[i].getName());
//                        System.out.println(fields[i].getInt(object));
                        intSerializer.writeInt(output,fields[i].getName(),fields[i].getInt(object));
                    }
                    else if(type==Double.TYPE)
                    {
                        // fields[i].setAccessible(true);
                        doubleSerializer.writeDouble(output, fields[i].getName(), fields[i].getDouble(object));
                    }
                    else if(type==String.class)
                    {
                        StringSerializer.writeString(output,fields[i].getName(),fields[i].get(object));
                    }
                    else
                    {
                        // objectSerializer.writeObject(output,fields[i].getName(),fields[i].get(object));
                    }
                }
                //处理类型是object
                else
                {
                    System.out.println("other class="+type);
                    fields[i].setAccessible(true);
                  //  objectSerializer.writeObject(output,fields[i].getName(),fields[i].get(object));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
