package novasIo;

import type.IntArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by novas on 16/2/28.
 */
public class BasicType {
    public static ArrayList<Type> arrayList=new ArrayList<>();
    public static void register()
    {
        arrayList.add(Integer.TYPE);
        arrayList.add(Boolean.TYPE);
        arrayList.add(Double.TYPE);
        arrayList.add(String.class);
        arrayList.add(IntArray.getType());
        arrayList.add(HashMap.class);
    }
    public static Type getBasicType(int index)
    {
        return arrayList.get(index);
    }
    public static int isBasicType(Type type)
    {
        int index=arrayList.indexOf(type);
        System.out.println("isbasictype="+type.getTypeName());
        // arrayList.indexOf(type);
        return index;
    }
}
