package core.sound;

import core.objects.entities.Camera;
import org.lwjgl.openal.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioMaster {

    private static long device;
    private static long context;

    private static List<Integer> buffers = new ArrayList<>();
    public static List<AudioSource> unusedAudio = new ArrayList<>();
    public static List<AudioSource> audioSources = new ArrayList<>();

    public static void init(){
        device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer) null);
        if (context == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);

        AL11.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
    }

    public static void setListenerData(Camera camera){
        ByteBuffer bb = ByteBuffer.allocateDirect( 6 * 4);
        bb.order( ByteOrder.nativeOrder() );
        FloatBuffer listenerOrientation = bb.asFloatBuffer();
        listenerOrientation.put( 0, camera.getViewMatrix().m01() );
        listenerOrientation.put( 1, camera.getViewMatrix().m02() );
        listenerOrientation.put( 2, camera.getViewMatrix().m03() );
        listenerOrientation.put( 3, camera.getViewMatrix().m11() );
        listenerOrientation.put( 4, camera.getViewMatrix().m12() );
        listenerOrientation.put( 5, camera.getViewMatrix().m13() );

        AL10.alListener3f(AL10.AL_POSITION, camera.getPosition().x,camera.getPosition().y,camera.getPosition().z);
        AL10.alListenerfv(AL10.AL_ORIENTATION, listenerOrientation);
        AL10.alListener3f(AL10.AL_VELOCITY, 0,0,0);
        AL10.alListenerf(AL10.AL_GAIN, 0.6f);
    }

    public static void destroyUnusedAudio(){
        Iterator i = unusedAudio.iterator();
        AudioSource source;
        while (i.hasNext()) {
            source = (AudioSource) i.next();
            if (!source.isPlaying()) {
                source.delete();
                i.remove();
            }
        }
    }

    public static int loadSound(String file){
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        WaveData waveFile = WaveData.create(file);
        AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();
        return buffer;
    }

    public static void deleteAllSources(){
        for (AudioSource audioSource : audioSources) {
            audioSource.delete();
        }

        audioSources.clear();
    }

    public static void cleanUp(){
        alcMakeContextCurrent(NULL);
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);

        for (Integer buffer : buffers) {
            AL10.alDeleteBuffers(buffer);
        }
    }
}
