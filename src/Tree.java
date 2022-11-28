import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Tree extends Unmovable {

    private int health;


    public Tree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        super(id, position, actionPeriod, animationPeriod, images);
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }




}
