package serializer;

import novasIo.Output;

/**
 * Created by novas on 16/2/29.
 */
public class doubleSerializer
{
    public static void writeDouble(Output output,String name,double value)
    {
        output.writeBasicFlag(Double.TYPE);
        output.writeString(name);
        output.writeDouble(value);
    }
}
