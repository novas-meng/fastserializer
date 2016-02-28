package novasIo;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by novas on 16/2/28.
 */
public class BasicType {
    public static ArrayList<Type> arrayList=new ArrayList<>();
    public static void register()
    {
        arrayList.add(Integer.TYPE);
    }
    public static Type getBasicType(int index)
    {
        return arrayList.get(index);
    }
    public static int isBasicType(Type type)
    {
        return arrayList.indexOf(type);
    }
}
