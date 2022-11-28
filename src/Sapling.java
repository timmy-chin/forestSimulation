import processing.core.PImage;

import java.util.List;

public class Sapling extends Unmovable implements HasActivity {

    private int healthLimit;
    private int health;

    public static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_HEALTH_LIMIT = 5;


    public Sapling(String id, Point position, List<PImage> images) {
        super(id, position, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, images);
        this.healthLimit = SAPLING_HEALTH_LIMIT;
        this.health = 0;

    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
        super.executeActivity(world, imageStore, scheduler);

    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        boolean res = super.transform(world, scheduler, imageStore);
        if (this.health >= this.healthLimit) {
            Tree tree = new Tree(TREE_KEY + "_" + super.getId(), super.getPosition(), Functions.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), Functions.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), Functions.getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), imageStore.getImageList(TREE_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return res;
    }




}
