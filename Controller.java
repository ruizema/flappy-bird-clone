package sample;

public class Controller {

    private int x = 0;

    public Controller(FlappyGhost flappyGhost) {
    }

    public int getGhostX() {
        return x++; // TODO: actually follow the ghost
    }
}
