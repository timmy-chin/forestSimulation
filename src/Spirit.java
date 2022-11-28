import processing.core.PImage;

import java.util.List;

public class Spirit extends Entity implements HasAnimation {

    private double animationPeriod;


    public Spirit(String id, Point position, double animationPeriod, List<PImage> images) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    @Override
    public double getAnimationPeriod() {
        return animationPeriod;
    }
}

