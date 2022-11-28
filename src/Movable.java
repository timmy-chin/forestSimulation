import processing.core.PImage;

import java.util.List;

public abstract class Movable extends HasMortality implements HasActivity {

    private double actionPeriod;
    private double animationPeriod;
    private int steps;
    private List<Point> points;

    public Movable(String id, Point position, double actionPeriod, double animationPeriod,  List<PImage> images, int health) {
        super(id, position, images, health);
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }

    public void setAnimationPeriod(double animationPeriod) {
        this.animationPeriod = animationPeriod;
    }

    public double getActionPeriod() {
        return actionPeriod;
    }


    protected abstract boolean _moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {



        if (Point.adjacent(this.getPosition(), target.getPosition())){
            return _moveTo(world, target, scheduler);
        }
        else {

            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
        
    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.getActionPeriod());
    }

    public Point nextPosition(WorldModel world, Point destPos) {

        PathingStrategy path = new AStarPathingStrategy();


        if (steps == 0) {

            points = path.computePath(this.getPosition(), destPos,
                    p -> world.withinBounds(p) && (!world.isOccupied(p) || world.getOccupancyCell(p).getClass() == Stump.class),
                    (p1, p2) -> Point.adjacent(p1, p2),
                    PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS);
            steps = 5;
        }

        if (points.size() == 0 || (world.isOccupied(points.get(0)) && world.getOccupancyCell(points.get(0)).getClass() != Stump.class)) {
            steps = 0;
            return this.getPosition();
        } else {
            steps--;
            return points.remove(0);
        }
    }


}
