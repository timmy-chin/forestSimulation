public class Activity implements Action{

    private WorldModel world;
    private ImageStore imageStore;
    private HasActivity entity;

    public Activity(HasActivity entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }
    

    

    public void executeAction(EventScheduler scheduler) {
            this.entity.executeActivity(this.world, this.imageStore, scheduler);
        }


    }

