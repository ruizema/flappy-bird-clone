import java.util.Random;

/**
 * Class for obstacles in Flappy Ghost.
 */
public class Obstacle extends Entity {

    private String type;
    private int init;
    private double timer;
    private int amplitude;
    private int period;
    private boolean passed;
    private int quantumRange;
    private double quantumTimer;

    /**
     * Constructor
     * @param x (Virtual) x-coordinate at the centre of the obstacle.
     * @param y Y-coordinate at the centre of the obstacle.
     * @param type The obstacle's type, which defines its behaviour.
     * @param imageNum The number of the fruit image associated with the obstacle.
     */
    public Obstacle(int x, int y, String type, int imageNum) {
        this.x = x;
        this.y = y;
        this.init = y;
        this.timer = 0;
        this.imageNum = imageNum;
        this.type = type;
        this.radius = 10 + (new Random()).nextInt(36);
        this.amplitude = 50;
        this.period = 2;
        this.quantumRange = 30;
        this.passed = false;
        this.inCollision = false;
    }

    /**
     * The update method is overrided and is used to update the obstacle's position in space, according to the time
     * passed. Each type of obstacle moves differently.
     * @param deltaTime The time (in seconds) since the last update of the position of the obstacle.
     */
    @Override
    public void update(double deltaTime) {
        timer += deltaTime;
        quantumTimer += deltaTime;
        switch (type) {
            case "sin":
                y = (int) (amplitude * Math.sin(period * timer * Math.PI) + init);
                break;
            case "quantum":
                if (quantumTimer >= 0.2) {
                    x += (int) (Math.random() * quantumRange * 2 + 1) - quantumRange;
                    y += (int) (Math.random() * quantumRange * 2 + 1) - quantumRange;
                    quantumTimer = 0;
                }
                break;
        }
    }

    /**
     * The getPassed method is a getter for the passed attribute of the obstacle.
     * @return boolean passed
     */
    public boolean getPassed() {
        return passed;
    }

    /**
     * The setPassed method is a setter for the passed attribute of the obstacle.
     */
    public void setPassed() {
        this.passed = true;
    }
}
