import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class Transformable extends Movable{

    private int resourceLimit;
    private int steps;
    private List<Point> points;


    public Transformable(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images, int resourceLimit, int health) {
        super(id, position, actionPeriod, animationPeriod, images, health);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void transform(Transformable dude, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }


}
