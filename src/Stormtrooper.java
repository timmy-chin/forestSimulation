import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Stormtrooper extends Imperial {
    private List<Point> points;
    private List<PImage> laserImage;
    private ImageStore imageStore;




    public Stormtrooper(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images, List<List<PImage>> imageType, List<PImage> laserImage, ImageStore imageStore, int health) {
        super(id, position, actionPeriod, animationPeriod, images, imageType, health);
        this.laserImage = laserImage;
        this.imageStore = imageStore;

    }



    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {



        if (this.getPosition().getY() == target.getPosition().getY() && Math.abs(this.getPosition().getX() - target.getPosition().getX()) >= 2){
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




    public boolean _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        Point laserSpawn;
        String direction;

        if (target.getPosition().getX() < this.getPosition().getX()){
            this.setImages(this.getImageType().get(0));
            laserSpawn = new Point(this.getPosition().getX() - 1, this.getPosition().getY());
            direction = "left";
        }
        else {
            this.setImages(this.getImageType().get(1));
            laserSpawn = new Point(this.getPosition().getX() + 1, this.getPosition().getY());
            direction = "right";
        }
        if (world.withinBounds(laserSpawn) && !world.isOccupied(laserSpawn)) {
            HasActivity laserblast = new LaserBlast(" ", laserSpawn, 0.05, 0.05, laserImage, direction);
            world.addEntity((Entity) laserblast);
            laserblast.scheduleActions(scheduler, world, imageStore);
        }

        return true;

    }

    public Point nextPosition(WorldModel world, Point destPos) {

        PathingStrategy path = new AStarPathingStrategy();


            points = path.computePath(this.getPosition(), destPos,
                    p -> world.withinBounds(p) && (!world.isOccupied(p) || world.getOccupancyCell(p).getClass() == Stump.class),
                    (p1, p2) -> p1.getY() == p2.getY() && (Math.abs(p1.getX() - p2.getX()) >= 2 && world.nothingInBetween(p1, p2, this)),
                    PathingStrategy.CARDINAL_NEIGHBORS);


        if (points.size() == 0) {
            return this.getPosition();
        } else {
            return points.remove(0);
        }
    }


}
