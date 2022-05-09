package core.audio;

import core.toolbox.Vector3;
import org.lwjgl.openal.AL10;

public class AudioSource {

    private int sourceId;

    public AudioSource(){
        sourceId = AL10.alGenSources();
        //AL10.alSourcef(sourceId, AL10.AL_ROLLOFF_FACTOR, 1);
        //AL10.alSourcef(sourceId, AL10.AL_REFERENCE_DISTANCE, 50);
        //AL10.alSourcef(sourceId, AL10.AL_MAX_DISTANCE, 400);
        setVolume(1);

        AudioMaster.audioSources.add(this);
    }

    public void pause(){
        AL10.alSourcePause(sourceId);
    }

    public void resume(){
        AL10.alSourcePlay(sourceId);
    }

    public void stop(){
        AL10.alSourceStop(sourceId);
    }

    public void autoDestroy(boolean bool){
        if (bool){
            AudioMaster.unusedAudio.add(this);
        }else {
            AudioMaster.unusedAudio.remove(this);
        }
    }

    public void delete(){
        stop();
        AL10.alDeleteSources(sourceId);
    }

    public void play(int buffer){
        stop();
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
        resume();
    }

    public void setLooping(boolean loop){
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public boolean isPlaying(){
        return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void setVelocity(Vector3 vel){
        AL10.alSource3f(sourceId, AL10.AL_VELOCITY, vel.x, vel.y, vel.z);
    }

    public void setVolume(float volume){
        AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
    }

    public void setPitch(float pitch){
        AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
    }

    public void setPosition(Vector3 pos){
        AL10.alSource3f(sourceId, AL10.AL_POSITION, pos.x, pos.y, pos.z);
    }
}
