package serializer;

import novasIo.BasicType;
import novasIo.Output;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by novas on 2016/3/1.
 */
public class mapSerializer
{
    /**对于hashmap，首先写入map的标识,变量名称，然后写入map的size,最大size为int的最大值；
     *
     * @param output not null
     * @param name not null
     * @param value notnull
     */
    public static void writeMap(Output output,String name,Object value)
    {
        Map map=(Map)value;
        output.writeBasicFlag(HashMap.class);
        output.writeString(name);
        output.writeInt(map.size());
        Set set=map.keySet();
        Iterator iterator=set.iterator();
        while (iterator.hasNext())
        {
          //  System.out.println("====");
            Object mapkey=iterator.next();
            Object mapValue=map.get(mapkey);
          //  System.out.println(iterator.next().getClass());
          //  System.out.println(mapkey.getClass());
         //   System.out.println(BasicType.isBasicType(mapkey.getClass()));
            int keyPtr=BasicType.isBasicType(mapkey.getClass());
            //ptr=-1 表示不是基本类型
            if(keyPtr!=-1)
            {
                if(keyPtr==3)
                {
                   // System.out.println("写入字符串");
                    StringSerializer.writeString(output,mapkey);
                }
            }
            int valuePtr=BasicType.isBasicType(mapValue.getClass());
            if(valuePtr==4)
            {
              //  System.out.println("写入int数组");
                intArraySerializer.writeIntArray(output,mapValue);
            }
           // System.out.println("valueptr="+valuePtr);
        }
    }

}
