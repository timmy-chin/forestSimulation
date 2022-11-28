import processing.core.PImage;

import java.util.List;

public abstract class Unmovable extends Entity implements HasActivity {

    private double actionPeriod;
    private double animationPeriod;

    public Unmovable(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }

    public double getActionPeriod() {
        return actionPeriod;
    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (!this.transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.getActionPeriod());
        }
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Entity stump = new Stump(STUMP_KEY + "_" + super.getId(), super.getPosition(), imageStore.getImageList(STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }
       return false;



    }

//    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        return this.transform(world, scheduler, imageStore);
//
//    }

    protected abstract int getHealth();
    protected abstract void setHealth(int health);



}
