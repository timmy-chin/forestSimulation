import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.*;

public final class VirtualWorld extends PApplet {
    private static String[] ARGS;

    public static final int VIEW_WIDTH = 960;
    public static final int VIEW_HEIGHT = 704;
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;

    public static final String IMAGE_LIST_FILE_NAME = "imagelist";
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    public static final int DEFAULT_IMAGE_COLOR = 0x808080;

    public static final String FAST_FLAG = "-fast";
    public static final String FASTER_FLAG = "-faster";
    public static final String FASTEST_FLAG = "-fastest";
    public static final double FAST_SCALE = 0.5;
    public static final double FASTER_SCALE = 0.25;
    public static final double FASTEST_SCALE = 0.10;

    public String loadFile = "world.sav";
    public long startTimeMillis = 0;
    public double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    private boolean click1 = false;
    private boolean click2 = false;
    private boolean click3 = false;
    private boolean click4 = false;


    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        parseCommandLine(ARGS);
        loadImages(IMAGE_LIST_FILE_NAME);
        loadWorld(loadFile, this.imageStore);

        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH, TILE_HEIGHT);
        this.scheduler = new EventScheduler();
        this.startTimeMillis = System.currentTimeMillis();
        this.scheduleActions(world, scheduler, imageStore);
    }

    public void draw() {
        double appTime = (System.currentTimeMillis() - startTimeMillis) * 0.001;
        double frameTime = (appTime - scheduler.getCurrentTime())/timeScale;
        this.update(frameTime);
        view.drawViewport();
    }

    public void update(double frameTime){
        scheduler.updateOnTime(frameTime);
    }

    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
    public void mousePressed() {
        Point pressed = mouseToPoint();

        if (pressed.getX() >= 6 && pressed.getX() <= 17 && pressed.getY() >= 24 && pressed.getY() <= 29 && !click1){
            click1 = true;
            this.world.clickZone1(imageStore);

            this.world.parseBadShip(new String[0], new Point(10,27), " ", imageStore);


            String[] properties = new String[3];
            properties[0] = "0.6";
            properties[1] = "0.4";
            properties[2] = "100";

            this.world.parseChrisVader(properties, new Point(11,26), " ", imageStore);
            Entity chrisVader = this.world.getOccupancyCell(new Point(11, 26));
            ((HasAnimation) chrisVader).scheduleActions(scheduler,world,imageStore);

            this.world.transformNearbyEntity(scheduler,world,imageStore, new Point(11,26), "stormtrooper");



        }
        if (pressed.getX() >= 24 && pressed.getX() <= 33 && pressed.getY() >= 25 && pressed.getY() <= 29 && !click2){
            click2 = true;
            this.world.clickZone2(imageStore);

            this.world.parseBadShip(new String[0], new Point(27,27), " ", imageStore);

            String[] properties = new String[3];
            properties[0] = "0.6";
            properties[1] = "0.4";
            properties[2] = "100";

            this.world.parseJosh(properties, new Point(28,26), " ", imageStore);
            Entity josh = this.world.getOccupancyCell(new Point(28, 26));
            ((HasAnimation) josh).scheduleActions(scheduler,world,imageStore);

            this.world.transformNearbyEntity(scheduler,world,imageStore, new Point(28,26), "stormtrooper");


        }

        if (pressed.getX() >= 28 && pressed.getX() <= 37 && pressed.getY() >= 0 && pressed.getY() <= 4 && !click3){
            click3 = true;
            this.world.clickZone3(imageStore);

            this.world.parseGoodShip(new String[0], new Point(31,0), " ", imageStore);

            String[] properties = new String[3];
            properties[0] = "0.6";
            properties[1] = "0.4";
            properties[2] = "100";

            this.world.parseKirstenSkywalker(properties, new Point(32,0), " ", imageStore);
            Entity kirstenSkywalker = this.world.getOccupancyCell(new Point(32, 0));
            ((HasAnimation) kirstenSkywalker).scheduleActions(scheduler,world,imageStore);

            this.world.transformNearbyEntity(scheduler,world,imageStore, new Point(32,0), "zuko");

        }

        if (pressed.getX() >= 10 && pressed.getX() <= 15 && pressed.getY() >= 0 && pressed.getY() <= 4 && !click4){
            click4 = true;
            this.world.clickZone4(imageStore);

            this.world.parseGoodShip(new String[0], new Point(11,0), " ", imageStore);

            String[] properties = new String[3];
            properties[0] = "0.6";
            properties[1] = "0.4";
            properties[2] = "100";

            this.world.parseAntoBiWan(properties, new Point(12,0), " ", imageStore);
            Entity ant = this.world.getOccupancyCell(new Point(12,0));
            ((HasAnimation) ant).scheduleActions(scheduler,world,imageStore);

            this.world.transformNearbyEntity(scheduler,world,imageStore, new Point(12,0), "kendama");


        }

        System.out.println("CLICK! " + pressed.getX() + ", " + pressed.getY());

        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            System.out.println(entity.getId() + ": " + entity.getClass() + " : ");
        }

    }

    public void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        for (Entity entity : world.getEntities()) {
             if (entity instanceof HasAnimation) {
                HasAnimation a = (HasAnimation) entity;
                a.scheduleActions(scheduler, world, imageStore);
            }
        }
    }

    private Point mouseToPoint() {
        return view.getViewport().viewportToWorld(mouseX / TILE_WIDTH, mouseY / TILE_HEIGHT);
    }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP -> dy -= 1;
                case DOWN -> dy += 1;
                case LEFT -> dx -= 1;
                case RIGHT -> dx += 1;
            }
            view.shiftView(dx, dy);
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME, imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        Arrays.fill(img.pixels, color);
        img.updatePixels();
        return img;
    }

    public void loadImages(String filename) {
        this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in,this);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadWorld(String file, ImageStore imageStore) {
        this.world = new WorldModel();
        try {
            Scanner in = new Scanner(new File(file));
            world.load(in, imageStore, createDefaultBackground(imageStore));
        } catch (FileNotFoundException e) {
            Scanner in = new Scanner(file);
            world.load(in, imageStore, createDefaultBackground(imageStore));
        }
    }

    public void parseCommandLine(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG -> timeScale = Math.min(FAST_SCALE, timeScale);
                case FASTER_FLAG -> timeScale = Math.min(FASTER_SCALE, timeScale);
                case FASTEST_FLAG -> timeScale = Math.min(FASTEST_SCALE, timeScale);
                default -> loadFile = arg;
            }
        }
    }

    public static void main(String[] args) {
        VirtualWorld.ARGS = args;
        PApplet.main(VirtualWorld.class);
    }

    public static List<String> headlessMain(String[] args, double lifetime){
        VirtualWorld.ARGS = args;

        VirtualWorld virtualWorld = new VirtualWorld();
        virtualWorld.setup();
        virtualWorld.update(lifetime);

        return virtualWorld.world.log();
    }
}
