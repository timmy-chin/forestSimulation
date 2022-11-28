import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Alliance extends StarWarsChar{

    public Alliance(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images, List<List<PImage>> imageType, int health) {
        super(id, position, actionPeriod, animationPeriod, images, imageType, health);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.StarWarsCharDeath(world,scheduler,imageStore);
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(ChrisVader.class, QuehaLoRen.class, Stormtrooper.class)));

        if (target.isPresent()) {
            this.moveTo(world, target.get(), scheduler);
        }
        super.executeActivity(world, imageStore, scheduler);

    }

    public void StarWarsCharDeath(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            HasAnimation spirit = new Spirit(STUMP_KEY + "_" + this.getId(), this.getPosition(), 0.9, imageStore.getImageList("spirit"));

            Optional<Entity> target;

            target = world.findRandom(this.getPosition(), new ArrayList<>(List.of(GoodShip.class)));


            if (target.isPresent()){
                List<Point> points = PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS.apply(target.get().getPosition()).filter(p -> world.withinBounds(p) && (!world.isOccupied(p) || world.getOccupancyCell(p).getClass() == Stump.class || world.getOccupancyCell(p).getClass() == Spirit.class)).limit(1).toList();
                if (points.size() == 0){
                    world.moveEntity(scheduler, this,  new Point(27, 4));

                }
                else {
                    world.moveEntity(scheduler, this, points.get(0));
                }
            }

            this.setHealth(this.getHealth_limit());

            Optional<Entity> randomSpirit = world.findRandom(this.getPosition(), new ArrayList<>(List.of(Spirit.class)));

            if (randomSpirit.isPresent()){
                world.removeEntity(scheduler, randomSpirit.get());
            }

            world.addEntity((Entity)spirit);
            spirit.scheduleActions(scheduler, world, imageStore);
        }

    }

}
