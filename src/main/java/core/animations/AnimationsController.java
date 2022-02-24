package core.animations;

import core.toolbox.RunNextFrame;

import java.util.ArrayList;
import java.util.List;

public class AnimationsController {

    static List<Animation> animationList = new ArrayList<>();

    public static void playAnimations(){
        for (Animation animation : animationList) {
            animation.animate();
        }
    }

    public static void addAnimation(Animation animation){
        RunNextFrame.runNextFrame(() -> animationList.add(animation));
    }

    public static void removeAnimation(Animation animation){
        RunNextFrame.runNextFrame(() -> animationList.remove(animation));
    }

    public static List<Animation> getAnimationList() {
        return animationList;
    }

    public static void cleanup(){
        animationList.clear();
    }
}
