import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    public static final String SAPLING_KEY = "sapling";
    public static final String TREE_KEY = "tree";
    public static final double TREE_ANIMATION_MAX = 0.600;
    public static final double TREE_ANIMATION_MIN = 0.050;
    public static final double TREE_ACTION_MAX = 1.400;
    public static final double TREE_ACTION_MIN = 1.000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;
    public static final String STUMP_KEY = "stump";


    public Entity(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;


    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log() {
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.getX(), this.position.getY(), this.imageIndex);
    }


    public String getId() {
        return this.id;
    }

    public Point getPosition() {
        return this.position;
    }

    public List<PImage> getImages() {
        return this.images;
    }

    public void setImages(List<PImage> images) {
        this.images = images;
    }

    public int getImageIndex() {
        return this.imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public void setPosition(Point pos) {
        this.position = pos;
    }

    // health starts at 0 and builds up until ready to convert to Tree




    // need resource count, though it always starts at 0


    // don't technically need resource count ... full


    public PImage getCurrentImage() {
        return this.getImages().get(this.getImageIndex() % this.getImages().size());

    }
}


