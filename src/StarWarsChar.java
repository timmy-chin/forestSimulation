import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class StarWarsChar extends Movable{

    private List<List<PImage>> imageType;


    public StarWarsChar(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images, List<List<PImage>> imageType, int health) {
        super(id, position, actionPeriod, animationPeriod, images, health);
        this.imageType = imageType;

    }


    public List<List<PImage>> getImageType() {
        return imageType;
    }


    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {



        if (Point.adjacent(this.getPosition(), target.getPosition())){

            return _moveTo(world, target, scheduler);
        }
        else {
            this.setImages(imageType.get(2));
            this.setAnimationPeriod(0.9);

            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }

    }



    public boolean _moveTo(WorldModel world, Entity target, EventScheduler scheduler) {

        if (target.getPosition().getX() < this.getPosition().getX()){
            this.setImages(this.getImageType().get(1));
            this.setAnimationPeriod(0.2);
        }
        else {
            this.setImages(this.getImageType().get(0));
            this.setAnimationPeriod(0.2);
        }
        HasMortality t = (HasMortality) target;
        t.setHealth(t.getHealth() - 1);
        return true;


    }
}
