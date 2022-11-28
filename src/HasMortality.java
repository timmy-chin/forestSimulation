import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HasMortality extends Entity{
    private int health_limit;
    private int health;

    public HasMortality(String id, Point position, List<PImage> images, int health) {
        super(id, position, images);
        this.health = health;
        this.health_limit = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth_limit() {
        return health_limit;
    }

    public void transformToSpirit(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Spirit spirit = new Spirit(STUMP_KEY + "_" + this.getId(), this.getPosition(), 0.9, imageStore.getImageList("spirit"));

            Optional<Entity> target = world.findRandom(this.getPosition(), new ArrayList<>(List.of(House.class)));

            if (target.isPresent()){
                List<Point> points = PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS.apply(target.get().getPosition()).filter(p -> world.withinBounds(p) && (!world.isOccupied(p) || world.getOccupancyCell(p).getClass() == Stump.class || world.getOccupancyCell(p).getClass() == Spirit.class)).limit(1).toList();
                if (points.size() == 0){
                    world.moveEntity(scheduler, this,  new Point(27, 4));

                }
                else {
                    world.moveEntity(scheduler, this, points.get(0));
                }
            }

            this.health = health_limit;

            Optional<Entity> randomSpirit = world.findRandom(this.getPosition(), new ArrayList<>(List.of(Spirit.class)));

            if (randomSpirit.isPresent()){
                world.removeEntity(scheduler, randomSpirit.get());
            }

            world.addEntity(spirit);
            spirit.scheduleActions(scheduler, world, imageStore);
        }

    }

}
