package serializer;

import novasIo.Output;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by novas on 2016/3/1.
 */
public class mapSerializer
{
    public static void writeMap(Output output,String name,Object value)
    {
        Map map=(Map)value;
        Set set=map.keySet();
        Iterator iterator=set.iterator();
        while (iterator.hasNext())
        {
            System.out.println("====");
            Object mapkey=iterator.next();
            Object mapValue=map.get(mapkey);
            if(mapkey instanceof String)
            {
                System.out.println("String");
            }
        }
    }

}
