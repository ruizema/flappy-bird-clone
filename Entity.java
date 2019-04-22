/**
 * The Entity class of Flappy Ghost game.
 */
public abstract class Entity {
    protected int imageNum;
    protected int x, y;
    protected int radius;
    protected boolean inCollision;

    /**
     * Getter for the X coordinate
     * @return double value of x position
     */
    public int getX() { return this.x; }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for the Y coordinate
     * @return double value of y position
     */
    public int getY() { return this.y; }
    /**
     * Getter for the rayon attribute
     * @return double value of rayon
     */
    public int getRadius() { return this.radius; }

    /**
     * Getter for the imageNum corresponding to the entity in question
     * @return imageNum of entity
     */
    public int getImageNum() { return this.imageNum; }

    /**
     * Getter for the attribute inCollision.
     * @return boolean inCoollision
     */
    public boolean getInCollision() {
        return inCollision;
    }

    /**
     * Setter for the inCollsion attribute.
     * @param inCollision
     */
    public void setInCollision(boolean inCollision) {
        this.inCollision = inCollision;
    }

    /**
     * Abstract method to update entity in the game
     * @param deltaTime
     */
    public abstract void update(double deltaTime);

}
