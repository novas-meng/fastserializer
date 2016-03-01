package type;

import java.lang.reflect.Type;

/**
 * Created by novas on 2016/3/1.
 */
public class IntArray
{
   public static Type getType()
   {
       int[] array=new int[1];
       return array.getClass();
   }

}
