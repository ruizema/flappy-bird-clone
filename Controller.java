package sample;

import javafx.scene.control.Button;

import java.util.LinkedList;
import java.util.Random;

public class Controller {

    private FlappyGhost view;
    private LinkedList<Entity> entities;
    private Ghost ghost;
    private double spawnTimer;
    private double SPAWN_FREQUENCY = 3;

    public Controller(FlappyGhost flappyGhost) {
        this.view = flappyGhost;
        this.spawnTimer = 0;
        this.entities = new LinkedList<>();
        this.ghost = new Ghost( view.getSTAGE_WIDTH() / 2, 200);
        entities.add(ghost);
    }

    public int getGhostX() {
        return ghost.getX();
    }

    public int getGhostScore() {
        return ghost.getScore();
    }

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

    public void update(double deltaTime) {
        spawnTimer += deltaTime;
        ghost.update(deltaTime);

        for (int i = 1; i < entities.size(); i++) {
            Obstacle obstacle = (Obstacle) entities.get(i);

            // Despawning obstacles
            if (obstacle.getX() < getGhostX() - 0.75 * view.getSTAGE_WIDTH()) {
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

    public void jump() {
        ghost.jump();
    }

    private void spawn() {
        Random rand = new Random();
        int x = getGhostX() + view.getSTAGE_WIDTH() / 2 + 45;
        int y = rand.nextInt(400);
        String type = new String[]{"simple", "sin", "quantum"}[rand.nextInt(3)];
        int imageNum = rand.nextInt(27);
        entities.add(new Obstacle(x, y, type, imageNum));
    }
}
