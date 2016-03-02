package serializer;

import novasIo.Output;
import type.IntArray;

/**
 * Created by novas on 2016/3/1.
 */
public class intArraySerializer
{
    public static void writeIntArray(Output output,String name,Object value)
    {
        output.writeBasicFlag(IntArray.getType());
        output.writeString(name);
        int[] array=(int[])value;
        output.writeArrayLength(array.length);
        for(int i=0;i<array.length;i++)
        {
            output.writeInt(array[i]);
        }
    }
    public static void writeIntArray(Output output,Object value)
    {
        output.writeBasicFlag(IntArray.getType());
        int[] array=(int[])value;
        output.writeArrayLength(array.length);
        for(int i=0;i<array.length;i++)
        {
            output.writeInt(array[i]);
        }
    }
}
