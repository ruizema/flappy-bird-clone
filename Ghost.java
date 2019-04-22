/**
 * The Ghost class
 */
public class Ghost extends Entity {

    private double vx;
    private double vy;
    private int gravity;
    private int score;

    /**
     * The constructor for the ghost player in the game
     * @param x x-coordinate at the centre of the obstacle.
     * @param y y-coordinate at the centre of the obstacle.
     */
    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        this.radius = 30;
        this.imageNum = -1;
        this.score = 0;
        this.vx = 120;
        this.vy = 0;
        this.gravity = 500;
    }

    /**
     * The update method is overrided from entity to be used for keeping up to date the position
     * and the speed of the ghost while the game is running.The ghost can't leave the game borders
     * and can't have a speed higher than 300px/s.
     * @param deltaTime is the time passed since the previous update was made to the ghost's speed and position
     */
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

    /**
     * The getScore metho is a getter for the score of the ghost in the game
     * @return score ,an int attribute of the points obtained
     */
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * The method jump is called when the spaceBar is used(by user) to make the ghost jump to avoid a collision with
     * the obstacles.
     */
    public void jump() {
        vy = -300;
    }

    /**
     * The checkPassed is used to keep track of how many obstacles the ghost was able to pass .When the left extremity
     * of the ghost passes the right extremity of the obstacle , the ghost has successfully passed the obstacle .Every
     * two obstacle passed by the ghost the speed increases of 15px/s.
     *
     * @param obstacle the obstacle in question that the ghost is passing
     */
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

    /**
     *  The checkCollision method is used to verify if the ghost has an intersection with an obstacle in the game.
     *  It will compare the the distance in between the two centers of the balls(balls=ghost & obstacles) versus their
     *  radius combined.
     * @param obstacle in potential collision with the ghost.
     * @return true if theirs a collision else false no collision
     */
    public boolean checkCollision(Obstacle obstacle) {
        double distance = Math.pow(x - obstacle.getX(), 2) + Math.pow(y - obstacle.getY(), 2);
        boolean inCollision = distance < Math.pow(radius + obstacle.getRadius(), 2);
        obstacle.setInCollision(inCollision);
        return inCollision;
    }
}
