package core.toolbox;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {

    public static float[] floatBufferToArray(FloatBuffer buf){
        float[] array;
        array = new float[buf.limit()];
        buf.get(array);

        return array;
    }

    public static int[] intBufferToArray(IntBuffer buf){
        int[] array;
        array = new int[buf.limit()];
        buf.get(array);

        return array;
    }
}
