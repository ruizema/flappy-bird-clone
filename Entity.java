package sample;

public abstract class Entity {
    protected int imageNum;
    protected int x, y;
    protected int radius;

    /**
     * Getter for the X coordinate
     * @return double value of x position
     */
    public int getX() { return this.x; }
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
     * Abstract method to update entity in the game
     * @param deltaTime
     */
    public abstract void update(double deltaTime);

}
