package sample;

public class Ghost extends Entity {

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        this.radius = 30;
        this.imageNum = -1;
    }

    @Override
    public void update(double deltaTime) {
        x += 120 * deltaTime;
    }
}
