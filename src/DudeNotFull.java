import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



public class DudeNotFull extends Transformable{

    private int resourceCount;





    public DudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images, int health) {
        super(id, position, actionPeriod, animationPeriod, images, resourceLimit, health);


    }

    @Override

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.transformToSpirit(world, scheduler, imageStore);
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Sapling.class, Tree.class)));


        if (target.isEmpty() || !this.moveTo(world, target.get(), scheduler) || !this.transform(world, scheduler, imageStore)) {
            super.executeActivity(world, imageStore, scheduler);
        }
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= super.getResourceLimit()) {
            DudeFull dude = new DudeFull(this.getId(), this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(), this.getResourceLimit(), this.getImages(), 5);

            super.transform(dude, world, scheduler, imageStore);

            scheduler.unscheduleAllEvents(this);


            return true;
        }

        return false;
    }

    public boolean _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
            Unmovable t = (Unmovable) target;
            this.resourceCount += 1;

            t.setHealth(t.getHealth() - 1);
            return true;

    }


}
