import processing.core.PImage;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel {
    private int numRows;
    private int numCols;
    private Background[][] background;
    private Entity[][] occupancy;
    private Set<Entity> entities;

    public static final String STUMP_KEY = "stump";
    public static final int STUMP_NUM_PROPERTIES = 0;

    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_HEALTH = 0;
    public static final int SAPLING_NUM_PROPERTIES = 1;

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_ANIMATION_PERIOD = 0;
    public static final int OBSTACLE_NUM_PROPERTIES = 1;

    public static final String DUDE_KEY = "dude";
    public static final int DUDE_ACTION_PERIOD = 0;
    public static final int DUDE_ANIMATION_PERIOD = 1;
    public static final int DUDE_LIMIT = 2;
    public static final int DUDE_NUM_PROPERTIES = 3;

    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 0;

    public static final String BADSHIP_KEY = "badship";
    public static final int BADSHIP_NUM_PROPERTIES = 0;

    public static final String GOODSHIP_KEY = "goodship";
    public static final int GOODSHIP_NUM_PROPERTIES = 0;

    public static final String FAIRY_KEY = "fairy";
    public static final int FAIRY_ANIMATION_PERIOD = 0;
    public static final int FAIRY_ACTION_PERIOD = 1;
    public static final int FAIRY_NUM_PROPERTIES = 2;

    public static final String TREE_KEY = "tree";
    public static final int TREE_ANIMATION_PERIOD = 0;
    public static final int TREE_ACTION_PERIOD = 1;
    public static final int TREE_HEALTH = 2;
    public static final int TREE_NUM_PROPERTIES = 3;

    public static final int PROPERTY_KEY = 0;


    public WorldModel() {

    }

    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log(){
        List<String> list = new ArrayList<>();
        for (Entity entity : entities) {
            String log = entity.log();
            if(log != null) list.add(log);
        }
        return list;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public Set<Entity> getEntities() {
        return entities;
    }



    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0 && pos.getX() < this.numCols;
    }

    public Optional<Entity> findNearest(Point pos, List<Class> cs) {
        List<Entity> ofType = new LinkedList<>();
        for (Class c : cs) {
            for (Entity entity : this.entities) {
                if (entity.getClass() == c) {
                    ofType.add(entity);
                }
            }
        }

        return pos.nearestEntity(ofType);
    }

    public Optional<Entity> findRandom(Point pos, List<Class> cs) {
        List<Entity> ofType = new LinkedList<>();
        for (Class c : cs) {
            for (Entity entity : this.entities) {
                if (entity.getClass() == c) {
                    ofType.add(entity);
                }
            }
        }

        return pos.randomEntity(ofType);
    }
    private void parseSapling(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            Entity entity = new Sapling(id, pt, imageStore.getImageList(SAPLING_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SAPLING_KEY, SAPLING_NUM_PROPERTIES));
        }
    }

    private void parseDude(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Entity entity = new DudeNotFull(id, pt, Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), Integer.parseInt(properties[DUDE_LIMIT]), imageStore.getImageList(DUDE_KEY), 5);
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

    public void parseChrisVader(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Entity entity = new ChrisVader(id, pt, Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), imageStore.getImageList("chrisvader"), List.of(imageStore.getImageList("CFL"), imageStore.getImageList("CFR"), imageStore.getImageList("chrisvader")), 15);
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

    public void parseJosh(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Entity entity = new QuehaLoRen(id, pt, Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), imageStore.getImageList("josh"), List.of(imageStore.getImageList("JFL"), imageStore.getImageList("JFR"), imageStore.getImageList("josh")), 15);
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

    public void parseKirstenSkywalker(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Entity entity = new KirstenSkywalker(id, pt, Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), imageStore.getImageList("kirstenskywalker"), List.of(imageStore.getImageList("KFL"), imageStore.getImageList("KFR"), imageStore.getImageList("kirstenskywalker")), 25);
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

    public void parseAntoBiWan(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Entity entity = new AntoBiWan(id, pt, Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), imageStore.getImageList("antobiwan"), List.of(imageStore.getImageList("AFL"), imageStore.getImageList("AFR"), imageStore.getImageList("antobiwan")), 15);
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }




    private void parseFairy(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            Entity entity = new Fairy(id, pt, Double.parseDouble(properties[FAIRY_ACTION_PERIOD]), Double.parseDouble(properties[FAIRY_ANIMATION_PERIOD]), imageStore.getImageList(FAIRY_KEY), 5);
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FAIRY_KEY, FAIRY_NUM_PROPERTIES));
        }
    }

    private void parseTree(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            Entity entity = new Tree(id, pt, Double.parseDouble(properties[TREE_ACTION_PERIOD]), Double.parseDouble(properties[TREE_ANIMATION_PERIOD]), Integer.parseInt(properties[TREE_HEALTH]), imageStore.getImageList(TREE_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", TREE_KEY, TREE_NUM_PROPERTIES));
        }
    }

    private void parseObstacle(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Entity entity = new Obstacle(id, pt, Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]), imageStore.getImageList(OBSTACLE_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", OBSTACLE_KEY, OBSTACLE_NUM_PROPERTIES));
        }
    }

    private void parseSpirit(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Entity entity = new Spirit(id, pt, Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]), imageStore.getImageList("spirit"));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "spirit", OBSTACLE_NUM_PROPERTIES));
        }
    }

    private void parseHouse(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            Entity entity = new House(id, pt, imageStore.getImageList(HOUSE_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }

    public void parseBadShip(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == BADSHIP_NUM_PROPERTIES) {
            Entity entity = new BadShip(id, pt, imageStore.getImageList(BADSHIP_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", BADSHIP_KEY, BADSHIP_NUM_PROPERTIES));
        }
    }

    public void parseGoodShip(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == GOODSHIP_NUM_PROPERTIES) {
            Entity entity = new GoodShip(id, pt, imageStore.getImageList(GOODSHIP_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", GOODSHIP_KEY, GOODSHIP_NUM_PROPERTIES));
        }
    }

    private void parseStump(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            Entity entity = new Stump(id, pt, imageStore.getImageList(STUMP_KEY));
            this.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", STUMP_KEY, STUMP_NUM_PROPERTIES));
        }
    }

    private void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        this.addEntity(entity);
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && this.getOccupancyCell(pos) != null;
    }

    public void addEntity(Entity entity) {
        if (this.withinBounds(entity.getPosition())) {
            this.setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }

    public void moveEntity(EventScheduler scheduler, Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            Optional<Entity> occupant = this.getOccupant(pos);
            occupant.ifPresent(target -> this.removeEntity(scheduler, target));
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(EventScheduler scheduler, Entity entity) {
        scheduler.unscheduleAllEvents(entity);
        this.removeEntityAt(entity.getPosition());
    }

    private void removeEntityAt(Point pos) {
        if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }


    public Optional<Entity> getOccupant(Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.getY()][pos.getX()];
    }

    private void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.getY()][pos.getX()] = entity;
    }

    public Optional<PImage> getBackgroundImage(Point pos) {
        if (this.withinBounds(pos)) {
            return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }



    private Background getBackgroundCell(Point pos) {
        return this.background[pos.getY()][pos.getX()];
    }

    private void setBackgroundCell(Point pos, Background background) {
        this.background[pos.getY()][pos.getX()] = background;
    }

    public void load(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        this.parseSaveFile(saveFile, imageStore, defaultBackground);
        if(this.background == null){
            this.background = new Background[this.numRows][this.numCols];
            for (Background[] row : this.background)
                Arrays.fill(row, defaultBackground);
        }
        if(this.occupancy == null){
            this.occupancy = new Entity[this.numRows][this.numCols];
            this.entities = new HashSet<>();
        }
    }
    private void parseSaveFile(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;
        while(saveFile.hasNextLine()){
            lineCounter++;
            String line = saveFile.nextLine().strip();
            if(line.endsWith(":")){
                headerLine = lineCounter;
                lastHeader = line;
                switch (line){
                    case "Backgrounds:" -> this.background = new Background[this.numRows][this.numCols];
                    case "Entities:" -> {
                        this.occupancy = new Entity[this.numRows][this.numCols];
                        this.entities = new HashSet<>();
                    }
                }
            }else{
                switch (lastHeader){
                    case "Rows:" -> this.numRows = Integer.parseInt(line);
                    case "Cols:" -> this.numCols = Integer.parseInt(line);
                    case "Backgrounds:" -> this.parseBackgroundRow(line, lineCounter-headerLine-1, imageStore);
                    case "Entities:" -> this.parseEntity(line, imageStore);
                }
            }
        }
    }
    public void parseBackgroundRow(String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if(row < this.numRows){
            int rows = Math.min(cells.length, this.numCols);
            for (int col = 0; col < rows; col++){
                this.background[row][col] = new Background(cells[col], imageStore.getImageList(cells[col]));
            }
        }
    }

    public void parseEntity(String line, ImageStore imageStore) {
        String[] properties = line.split(" ", Functions.ENTITY_NUM_PROPERTIES + 1);
        if (properties.length >= Functions.ENTITY_NUM_PROPERTIES) {
            String key = properties[PROPERTY_KEY];
            String id = properties[Functions.PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[Functions.PROPERTY_COL]), Integer.parseInt(properties[Functions.PROPERTY_ROW]));

            properties = properties.length == Functions.ENTITY_NUM_PROPERTIES ?
                    new String[0] : properties[Functions.ENTITY_NUM_PROPERTIES].split(" ");

            switch (key) {
                case OBSTACLE_KEY -> this.parseObstacle(properties, pt, id, imageStore);
                case DUDE_KEY -> this.parseDude(properties, pt, id, imageStore);
                case FAIRY_KEY -> this.parseFairy(properties, pt, id, imageStore);
                case HOUSE_KEY -> this.parseHouse(properties, pt, id, imageStore);
                case TREE_KEY -> this.parseTree(properties, pt, id, imageStore);
                case SAPLING_KEY -> this.parseSapling(properties, pt, id, imageStore);
                case STUMP_KEY -> this.parseStump(properties, pt, id, imageStore);
                case "chrisvader" -> this.parseChrisVader(properties, pt, id, imageStore);
                case "spirit" -> this.parseSpirit(properties, pt, id, imageStore);
                case "kirstenskywalker" -> this.parseKirstenSkywalker(properties, pt, id, imageStore);
                case "josh" -> this.parseJosh(properties, pt, id, imageStore);
                case "antobiwan" -> this.parseAntoBiWan(properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }
        }else{
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public void clickZone1(ImageStore imageStore){
        for (int row = 6; row <= 17; row++ ){
            for (int col = 24; col <= 29; col++){
                if (Math.abs(11-row)+Math.abs(29-col) <= 7){
                    int randint = (int) (Math.random() * 10);
                    if (randint > 4){
                        setBackgroundCell(new Point(row,col), new Background("firegrass", imageStore.getImageList("firegrass")));
                    }
                    else{
                        setBackgroundCell(new Point(row,col), new Background("burntgrass", imageStore.getImageList("burntgrass")));

                    }

                }
            }
        }


    }


    public void clickZone2(ImageStore imageStore) {
        for (int row = 24; row <= 33; row++) {
            for (int col = 25; col <= 29; col++) {
                if (Math.abs(28 - row) + Math.abs(29 - col) <= 6) {
                    int randint = (int) (Math.random() * 10);
                    if (randint > 4) {
                        setBackgroundCell(new Point(row, col), new Background("smokegrass", imageStore.getImageList("smokegrass")));
                    } else {
                        setBackgroundCell(new Point(row, col), new Background("burntgrass", imageStore.getImageList("burntgrass")));

                    }

                }
            }
        }
    }

    public void clickZone3(ImageStore imageStore) {
        for (int row = 28; row <= 37; row++) {
            for (int col = 0; col <= 4; col++) {
                if (Math.abs(33 - row) + Math.abs(-col) <= 6) {
                    int randint = (int) (Math.random() * 10);
                    if (randint > 3) {
                        setBackgroundCell(new Point(row, col), new Background("flowergrass", imageStore.getImageList("flowergrass")));
                    } else {
                        setBackgroundCell(new Point(row, col), new Background("grass", imageStore.getImageList("grass")));

                    }

                }
            }
        }
    }

    public void clickZone4(ImageStore imageStore) {
        for (int row = 10; row <= 15; row++) {
            for (int col = 0; col <= 4; col++) {
                if (Math.abs(13 - row) + Math.abs(-col) <= 5) {
                    int randint = (int) (Math.random() * 10);
                    if (randint > 3) {
                        setBackgroundCell(new Point(row, col), new Background("flowergrass", imageStore.getImageList("flowergrass")));
                    } else {
                        setBackgroundCell(new Point(row, col), new Background("grass", imageStore.getImageList("grass")));

                    }

                }
            }
        }
    }

    public void transformNearbyEntity(EventScheduler scheduler, WorldModel world, ImageStore imageStore, Point position, String spawnType){
        for (int i = 0; i < 3; i++){
            Optional<Entity> nearbyEntity = findNearest(position,  new ArrayList<>(List.of(DudeFull.class, DudeNotFull.class, Fairy.class)));

            if (nearbyEntity.isPresent()){
                Point nearbyPosition = nearbyEntity.get().getPosition();
                removeEntity(scheduler, nearbyEntity.get());

                Entity spawn = null;

                switch (spawnType) {
                    case "zuko" -> spawn = new Zuko(" ", nearbyPosition, 0.6, 0.2, imageStore.getImageList("zuko"), List.of(imageStore.getImageList("ZFL"), imageStore.getImageList("ZFR"), imageStore.getImageList("zuko")), 15);
                    case "kendama" -> spawn = new Kendama(" ", nearbyPosition, 0.6, 0.2, imageStore.getImageList("kendama"), List.of(imageStore.getImageList("KDFL"), imageStore.getImageList("KDFR"), imageStore.getImageList("kendama")), 10);
                    case "stormtrooper" -> spawn = new Stormtrooper(" ", nearbyPosition, 0.6, 0.2, imageStore.getImageList("stormtrooper"), List.of(imageStore.getImageList("SFR"), imageStore.getImageList("stormtrooper"), imageStore.getImageList("stormtrooper")), imageStore.getImageList("laserblast"), imageStore, 5);
                    default -> spawn = new Zuko(" ", nearbyPosition, 0.6, 0.2, imageStore.getImageList("zuko"), List.of(imageStore.getImageList("ZFL"), imageStore.getImageList("ZFR"), imageStore.getImageList("zuko")), 10);
                }

                addEntity(spawn);
                ((HasAnimation) spawn).scheduleActions(scheduler,world,imageStore);


                }
            }
        }

        public boolean nothingInBetween(Point p1, Point p2, Entity entity) {

        boolean res = true;

            if (p1.getX() < p2.getX()) {
                for (int i = 1; Math.abs(p1.getX() - p2.getX()) > i; i++) {
                    Point nextPos = new Point(p1.getX() + i, p1.getY());
                    if (isOccupied(nextPos) && getOccupancyCell(nextPos) != entity){
                        res = false;
                        break;
                    }
                }
            }
            else {
                for (int i = 1; Math.abs(p1.getX() - p2.getX()) > i; i++) {
                    Point nextPos = new Point(p1.getX() - i, p1.getY());
                    if (isOccupied(nextPos) && getOccupancyCell(nextPos) != entity){
                        res = false;
                        break;
                    }
                }
            }
            return res;
        }
    }





