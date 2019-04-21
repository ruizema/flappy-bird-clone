package sample;

public class Ghost extends Entity {

    private double vx;
    private double vy;
    private int gravity;
    private int score;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        this.radius = 30;
        this.imageNum = -1;
        this.vx = 120;
        this.vy = 0;
        this.score = 0;
        this.gravity = 500;
    }

    @Override
    public void update(double deltaTime) {
        x += vx * deltaTime;
        vy += gravity * deltaTime;
        if (vy > 300) {
            vy = 300;
        }
        y += vy * deltaTime;

        boolean lowerBound = y >= 400 - radius;
        boolean upperBound = y < radius;
        if (lowerBound || upperBound) {
            if (lowerBound) {
                y = 400 - radius - 1;
            } else {
                y = radius;
            }
            vy = -vy;
        }
    }

    public int getScore() {
        return score;
    }

    public void jump() {
        vy = -300;
    }

    public void checkPass(Obstacle obstacle) {
        if (!obstacle.getPassed() && x - radius > obstacle.getX() + obstacle.getRadius()) {
            obstacle.setPassed();
            score += 5;
            if (score % 10 == 0) {
                vx += 15;
                gravity += 15;
            }
        }
    }

    public boolean checkCollision(Obstacle obstacle) {
        double distance = Math.pow(x - obstacle.getX(), 2) + Math.pow(y - obstacle.getY(), 2);
        return distance < Math.pow(radius + obstacle.getRadius(), 2);
    }
}
