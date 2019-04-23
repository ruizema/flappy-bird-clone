import java.util.LinkedList;
import java.util.Random;

/**
 * The Controller of the Flappy Ghost game.
 */
public class Controller {

    private FlappyGhost view;
    private LinkedList<Entity> entities;
    private Ghost ghost;
    private double spawnTimer;
    private double SPAWN_FREQUENCY = 3;

    /**
     * Constructor of the controller.
     * @param flappyGhost the actual game
     */
    public Controller(FlappyGhost flappyGhost) {
        this.view = flappyGhost;
        this.spawnTimer = 0;
        this.entities = new LinkedList<>();
        this.ghost = new Ghost( view.getSTAGE_WIDTH() / 2, 200);
        entities.add(ghost);
    }

    /**
     * Getter for the x coordinate of the ghost in the game.
     * @return int x position of ghost
     */
    public int getGhostX() {
        return ghost.getX();
    }

    /**
     * Getter for the score of the ghost ine the game.
     * @return int value of score
     */
    public int getGhostScore() {
        return ghost.getScore();
    }

    /**
     * The getEntityCoordinates method is used to store the information concerning the entities(Ghost,Obstacles)
     * in the game.
     * @return 2D array of int values of information on the various entities in game
     */
    public int[][] getEntityCoordinates() {
        int[][] coordinates = new int[entities.size()][5];
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            coordinates[i][0] = entity.getImageNum();
            coordinates[i][1] = entity.getX();
            coordinates[i][2] = entity.getY();
            coordinates[i][3] = entity.getRadius();
            coordinates[i][4] = entity.getInCollision() ? 1 : 0;
        }
        return coordinates;
    }

    /**
     * The update method is overrided , it is used to keep track of what is going on in the game.This method makes sure
     * to update all the entities during the game.
     * @param deltaTime the time passed since the last update
     */
    public void update(double deltaTime) {
        spawnTimer += deltaTime;
        ghost.update(deltaTime);

        for (int i = 1; i < entities.size(); i++) {
            Obstacle obstacle = (Obstacle) entities.get(i);

            // Despawning obstacles
            double leftBound = getGhostX() - 0.75 * view.getSTAGE_WIDTH();
            if (obstacle.getX() < leftBound) {
                entities.remove(i);
                i--;
                continue;
            }

            // Updating obstacles
            obstacle.update(deltaTime);

            // Checking for interactions
            if (ghost.checkCollision(obstacle)) {
                System.out.print("");
            }
            ghost.checkPass(obstacle);
        }

        // Spawning obstacles
        if (spawnTimer >= SPAWN_FREQUENCY) {
            spawn();
            spawnTimer = 0;
        }
    }

    /**
     * This method is used to make the ghost jump when the user presses the spaceBar.
     */
    public void jump() {
        ghost.jump();
    }

    /**
     * The spawn method is used to generate (used in the Obstacle class)randomly every three seconds a new
     * obstacles and choosing randomly a picture from the 27 pictures of fruits.
     */
    private void spawn() {
        Random rand = new Random();
        int x = getGhostX() + view.getSTAGE_WIDTH() / 2 + 45;
        int y = rand.nextInt(400);
        String type = new String[]{"simple", "sin", "quantum"}[rand.nextInt(3)];
        int imageNum = rand.nextInt(27);
        entities.add(new Obstacle(x, y, type, imageNum));
    }

    /**
     * This method is called to reset the game by generating a new instance of ghost at the starting point, as well as
     * despawning all other entities.
     */
    public void reset() {
        ghost = new Ghost( view.getSTAGE_WIDTH() / 2, 200);
        entities.set(0, ghost);
        for (int i = 1; i < entities.size(); i++) {
            entities.remove(i);
            i--;
        }
    }
}
