
import novasIo.Input;
import novasIo.Output;
import novasIo.novas;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by novas on 16/2/28.
 */
public class demo {
    public static void main(String[] args)throws Exception
    {
        father father=new father();
        father.a=89;
        father.c=98888.7;
        novas novas=new novas();
        Output output=new Output("file.bin");
        output.writeObject(father);
        Input input=new Input("file.bin");
        father=(father)input.readObject();
        System.out.println(father.a);
        System.out.println(father.c);
    }
}
