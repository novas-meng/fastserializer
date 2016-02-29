package serializer;

import novasIo.Output;

/**
 * Created by novas on 16/2/28.
 */
public class intSerializer
{

    //从字节数组中读取基本信息，返回
    //写int数组的时候，首先写入1，表示基本类型,2表示不是基本类型
    //然后写入基本类型的索引，比如1表示int
    //然后写入名称长度，名称，最后是值
    public static void writeInt(Output output,String name,int value)
    {
        output.writeBasicFlag(Integer.TYPE);
        output.writeString(name);
        output.writeInt(value);
    }
}
