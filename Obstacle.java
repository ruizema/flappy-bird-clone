package sample;

import java.util.Random;

public class Obstacle extends Entity {

    private String type;
    private int init;
    private double timer;

    public Obstacle(int x, int y, String type, int imageNum) {
        this.x = x;
        this.y = y;
        this.init = y;
        this.timer = 0;
        this.imageNum = imageNum;
        this.type = type;
        this.radius = 10 + (new Random()).nextInt(36);
    }

    @Override
    public void update(double deltaTime) {
        timer += deltaTime;
        switch (type) {
            case "sinus":
                y = (int) (50 * Math.sin(timer % 2)) + init;
            case "quantum":
                break;
        }
    }
}
