package sample;

import java.util.Random;

public class Obstacle extends Entity {

    private String type;
    private int init;
    private double timer;
    private int amplitude;
    private int period;
    private boolean passed;
    private int quantumRange;
    private double quantumTimer;

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
    }

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
                    int quantumMovement = (int) (Math.random() * quantumRange * 2 + 1) - quantumRange;
                    x += quantumMovement;
                    y += quantumMovement;
                    quantumTimer = 0;
                }
                break;
        }
    }
}
