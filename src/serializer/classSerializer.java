package serializer;

import novasIo.Input;
import novasIo.Output;

/**
 * Created by novas on 16/2/28.
 */
public class classSerializer
{
    public static Class readClass(Input input)
    {
        return input.readClass();
    }
    public static void writeClass(Output output,Class cls)
    {
        output.writeObjectType();
        String clsname=cls.getName();
        output.writeString(clsname);
    }
    public static void writeObjectName(Output output,String name)
    {
        output.writeString(name);
    }
}
