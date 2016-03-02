package serializer;

import novasIo.BasicType;
import novasIo.Input;
import novasIo.Output;
import org.omg.PortableInterceptor.INACTIVE;
import type.IntArray;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by novas on 16/2/28.
 */

/**
 * 生成序列化的规则；1.0
 * 首先对于一个等待序列化的对象A，
 * 1:首先写入A的类名
 * 2:然后writeObjectType()，表示写入的是一个对象，然后写入A对象的名称。然后写入A中变量Field的个数
 *  output.writeString(name);
    output.writeInt(fields.length);
 * 3:然后依次遍历每个field；分两种情况
 * （1）：field是基本类型；
 * 首先写入1，表示是基本类型，然后写入基本类型的索引
 * output.writeBasicFlag(String.class);
 * 然后写入变量名，然后写入变量值
 * （2）:field不是基本类型
 * 首先writeObjectType()，表示field是对象，然后写入变量名和field的长度，根据getType获取类名；
 */
public class objectSerializer
{
    public static Class readObjectClass(Input input)
    {
        int ptr=input.isBasicType();
        Class cls=input.readClass();
        System.out.println("read class"+cls.getName());
        return cls;
    }
    //dector表示是否是顶层的对象，当dector为true的时候，表示读取的是序列化对象的fields，这个时候
    //cls为之前读取的class，当dector为false的时候，表示读取的是对象中变量包含的fields；这个时候不需要再
    // input.isBasicType();因为在for循环的时候已经读取了，而且这个时候，需要的class是此变量的class
    public static Object readObjectFields(Class cls,Input input,boolean dector,Object srcObject)
    {
        String name=null;
        int length=0;
        Field Parntfield=null;
            try
            {
                if(dector==true)
                {
                    input.isBasicType();
                     name=input.readObjectName();
                     length=input.readInt();
                }
                else
                {
                    name=input.readObjectName();
                    length=input.readInt();
                    Parntfield=cls.getDeclaredField(name);
                    cls=Parntfield.getType();
                }

                Object valueobject=cls.newInstance();
                for(int i=0;i<length;i++)
                {
                    int ptr=input.isBasicType();
                    if(ptr==1)
                    {
                        // System.out.println("基本类型" + input.isBasicType());
                        Type type=input.readBasicType();
                        String m=input.readFieldName();
                        Object value=input.readValue(type);
                      //  System.out.println("basic name=" + m);
                        Field field=cls.getDeclaredField(m);
                        field.setAccessible(true);
                        field.set(valueobject,value);
                        if(type==Integer.TYPE)
                        {
                           // System.out.println("integer");
                        }
                        if(type==Double.TYPE)
                        {
                           // System.out.println("Double");
                        }
                        if(type==IntArray.getType())
                        {
                           // System.out.println("IntArray");
                        }
                        if(type==HashMap.class)
                        {
                           // System.out.println("hashmap");
                        }
                    }
                    else if(ptr==2)
                    {
                     //   System.out.println("not basic type");
                        //  objectSerializer.readObject(input);
                       // Class subcls=input.readClass();
                       // System.out.println("class="+subcls.getName());

                      //  String fieldname=input.readFieldName();
                      //  System.out.println("name="+fieldname);
                     //   int fieldlength=input.readInt();
                     //   System.out.println("fieldlength="+fieldlength);
                      //  Class subcls=cls.getDeclaredField(fieldname).getType();
/*
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
*/
                        Object fieldObject=readObjectFields(cls,input,false, valueobject);
                    }
                }
                if(dector==false)
                {
                  //  System.out.println("Parntfield+"+Parntfield.getName());
                    Parntfield.setAccessible(true);
                    Parntfield.set(srcObject,valueobject);
                }
                return valueobject;
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    //根据字节数组和反射生成字object信息
    public static Object readObject(Input input)
    {
        Class cls=readObjectClass(input);
        return readObjectFields(cls,input,true,null);
    }
    public static void writeObjectClass(Output output,String name,Object SrcObject)
    {
        Class cls=SrcObject.getClass();
     //   System.out.println("current "+cls.getName());
        classSerializer.writeClass(output, cls);
        writeObject(output,name,SrcObject);
    }
    //根据反射进行写入object信息,必须先写名字，和包含的字段的个数
    public static void writeObject(Output output,String name,Object srcObject)
    {
      //  long time=System.currentTimeMillis();
        Class cls=srcObject.getClass();
       // long time1=System.currentTimeMillis();
        // classSerializer.writeObjectName(output, name);
       // System.out.println("current "+cls.getName());
       // classSerializer.writeClass(output, cls);
       // classSerializer.writeObjectName(output, name);
        Field[] fields=cls.getDeclaredFields();
        // StringSerializer.writeString(output,);
      //  System.out.println("=========="+name+"  "+fields.length);
        output.writeObjectType();
        output.writeString(name);
        output.writeInt(fields.length);
     //   System.out.println("time=" + (time1 - time));
        // intSerializer.writeInt(output,name,fields.length);
        for(int i=0;i<fields.length;i++)
        {
         //   System.out.println("field="+fields[i].getName());
            Type type=fields[i].getType();
            int index= BasicType.isBasicType(type);
            try
            {
                //处理类型是基本类型
                if(index!=-1)
                {
                    fields[i].setAccessible(true);
                    if(type==Integer.TYPE)
                    {
                      //  System.out.println(fields[i].getName());
//                        System.out.println(fields[i].getInt(object));
                        intSerializer.writeInt(output,fields[i].getName(),fields[i].getInt(srcObject));
                    }
                    else if(type==Double.TYPE)
                    {
                        // fields[i].setAccessible(true);
                        doubleSerializer.writeDouble(output, fields[i].getName(), fields[i].getDouble(srcObject));
                    }
                    else if(type==String.class)
                    {
                        StringSerializer.writeString(output,fields[i].getName(),fields[i].get(srcObject));
                    }
                    else if(type== IntArray.getType())
                    {
                        intArraySerializer.writeIntArray(output,fields[i].getName(),fields[i].get(srcObject));
                        // objectSerializer.writeObject(output,fields[i].getName(),fields[i].get(object));
                    }
                    else if(type== HashMap.class)
                    {
                       // System.out.println("this is a map");
                        mapSerializer.writeMap(output, fields[i].getName(), fields[i].get(srcObject));
                    }
                }
                //处理类型是object
                else
                {
                  //  System.out.println("other class=" + type);
                    fields[i].setAccessible(true);
                    objectSerializer.writeObject(output,fields[i].getName(),fields[i].get(srcObject));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
