import processing.core.PImage;

import java.util.List;

public class Animation implements Action{
    private int repeatCount;
    private HasAnimation entity;


    public Animation(HasAnimation entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }


    public void executeAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity, new Animation(this.entity, Math.max(this.repeatCount - 1, 0)), this.entity.getAnimationPeriod());
        }
    }




}
