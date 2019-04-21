package sample;

public class Ghost extends Entity {

    private int speed;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        this.radius = 30;
        this.imageNum = -1;
        this.speed = 120;
    }

    @Override
    public void update(double deltaTime) {
        x += speed * deltaTime;
    }
}
