package sample;

import java.util.LinkedList;
import java.util.Random;

public class Controller {

    private int x = 0;
    private FlappyGhost view;
    private LinkedList<Entity> entities;
    private double lastSpawned;

    public Controller(FlappyGhost flappyGhost) {
        this.view = flappyGhost;
        this.lastSpawned = 0;
        entities = new LinkedList<>();
        entities.add(new Ghost( view.getSTAGE_WIDTH() / 2, 200));
    }

    public int getGhostX() {
        return entities.get(0).getX();
    }

    public int[][] getEntityCoordinates() {
        int[][] coordinates = new int[entities.size()][4];
        for (int i = 0; i < entities.size(); i++) {
            coordinates[i][0] = entities.get(i).getImageNum();
            coordinates[i][1] = entities.get(i).getX();
            coordinates[i][2] = entities.get(i).getY();
            coordinates[i][3] = entities.get(i).getRadius();
        }
        return coordinates;
    }

    public void update(double deltaTime) {

        for (int i = 0; i < entities.size(); i++) {

            // Despawning obstacles
            if (i != 0 && entities.get(i).getX() < getGhostX() - 0.75 * view.getSTAGE_WIDTH()) {
                entities.remove(i);
                i--;
                continue;
            }

            // Updating entities
            entities.get(i).update(deltaTime);

            // Spawning obstacles
            double SPAWN_FREQUENCY = 3;
            double nanoTime = System.nanoTime();
            double spawnDeltaTime = (nanoTime - lastSpawned) * 1e-9;
            if (spawnDeltaTime >= SPAWN_FREQUENCY || lastSpawned == 0) {
                spawn();
                lastSpawned = nanoTime;
            }

        }
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
