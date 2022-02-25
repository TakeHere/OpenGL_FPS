package core.sound;

import core.toolbox.Vector3;

public class Test {

    public static void main(String[] args) {
        AudioMaster.init();
        //AudioMaster.setListenerData(new Vector3(0,0,0));

        int buffer = AudioMaster.loadSound("res/sounds/bounce.wav");
        AudioSource audioSource = new AudioSource();
        audioSource.setLooping(true);
        audioSource.play(buffer);

        char c = ' ';

        Vector3 pos = new Vector3(30,0,0);

        while (c != 'q'){
            pos.x -= 0.1f;
            audioSource.setPosition(pos);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        AudioMaster.cleanUp();
    }
}
