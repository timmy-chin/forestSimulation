import processing.core.PImage;

import java.util.List;

public interface HasAnimation {

    default void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());

    }


    double getAnimationPeriod();

    default void nextImage() {
        this.setImageIndex(this.getImageIndex() + 1);
    }

    int getImageIndex();
    void setImageIndex(int num);


}
