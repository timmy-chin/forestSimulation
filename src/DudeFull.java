import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeFull extends Transformable {





    public DudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images, int health){
        super(id, position, actionPeriod, animationPeriod, images, resourceLimit, health);


    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.transformToSpirit(world, scheduler, imageStore);
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(House.class)));


        if (target.isPresent() && this.moveTo(world, target.get(), scheduler)) {
            this.transform(world, scheduler, imageStore);
        } else {
            super.executeActivity(world, imageStore, scheduler);
        }
    }

    private void transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        DudeNotFull dude = new DudeNotFull(this.getId(), this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(), this.getResourceLimit(), this.getImages(), 5);

        super.transform(dude, world, scheduler, imageStore);
    }

    public boolean _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        return true;
    }

}
