package serializer;

import novasIo.Output;

/**
 * Created by novas on 16/2/29.
 */
public class StringSerializer
{
    public static void writeString(Output output,String name,Object value)
    {
        output.writeBasicFlag(String.class);
        output.writeString(name);
        output.writeValueString(value.toString());
    }
    public static void writeString(Output output,Object value)
    {
        output.writeBasicFlag(String.class);
        output.writeString(value.toString());
    }
}
