import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Fairy extends Movable {

    private int steps;
    private List<Point> points;


    public Fairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images, int health) {
        super(id, position, actionPeriod, animationPeriod, images, health);

    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.transformToSpirit(world, scheduler, imageStore);
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Stump.class)));


        if (target.isPresent()) {
            Point tgtPos = target.get().getPosition();
            if (this.moveTo(world, target.get(), scheduler)) {

                Sapling sapling = new Sapling(SAPLING_KEY + "_" + target.get().getId(), tgtPos, imageStore.getImageList(SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        super.executeActivity(world, imageStore, scheduler);
    }

    public boolean _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
            world.removeEntity(scheduler, target);
            return true;
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy path = new SingleStepPathingStrategy();

        if (steps == 0) {

            points = path.computePath(this.getPosition(), destPos,
                    p -> world.withinBounds(p) && (!world.isOccupied(p)),
                    (p1, p2) -> Point.adjacent(p1, p2),
                    PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
            steps = 5;
        }

        if (points.size() == 0 || world.isOccupied(points.get(0))){
            steps = 0;
            return this.getPosition();
        }
        else{
            steps--;
            return points.remove(0);



        }
    }




}
