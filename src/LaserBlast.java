import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LaserBlast extends Entity implements HasActivity{

    private double actionPeriod;
    private double animationPeriod;
    private String direction;



    public LaserBlast(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images, String direction) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.direction = direction;

    }

    @Override
    public double getActionPeriod() {
        return actionPeriod;
    }

    @Override
    public double getAnimationPeriod() {
        return animationPeriod;
    }

    @Override

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.moveTo(world, scheduler, imageStore);

    }




    public void moveTo(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        Point nextPos;

        switch (direction){
            case "right" -> nextPos = new Point(this.getPosition().getX() + 1, this.getPosition().getY());
            case "left" -> nextPos = new Point(this.getPosition().getX() - 1, this.getPosition().getY());
            default -> nextPos = new Point(this.getPosition().getX() + 1, this.getPosition().getY());
        }

        if (world.withinBounds(nextPos) && !world.isOccupied(nextPos)){
            world.moveEntity(scheduler,this, nextPos);
            scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.getActionPeriod());
        }

        else if (world.withinBounds(nextPos) && world.getOccupancyCell(nextPos) instanceof HasMortality) {
            HasMortality e = ((HasMortality) world.getOccupancyCell(nextPos));
            e.setHealth(e.getHealth() - 1);
            world.removeEntity(scheduler, this);
        }
        else{
            world.removeEntity(scheduler, this);
        }

    }



}
