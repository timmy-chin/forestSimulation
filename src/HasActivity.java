public interface HasActivity extends HasAnimation {

    default void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.getActionPeriod());
        HasAnimation.super.scheduleActions(scheduler, world, imageStore);
    }

    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    double getActionPeriod();


}
