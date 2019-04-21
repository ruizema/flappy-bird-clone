package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FlappyGhost extends Application {

    private Stage stage;
    private Controller controller;

    private int STAGE_WIDTH = 640;

    public int getSTAGE_WIDTH() {
        return STAGE_WIDTH;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.controller = new Controller(this);
        primaryStage.setResizable(false);

        // Starting screen

        // Main game
        Pane gameRoot = new Pane();
        Scene gameScene = new Scene(gameRoot, STAGE_WIDTH, 440);
        Canvas canvas = new Canvas(STAGE_WIDTH, 400);
        gameRoot.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();

        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                controller.jump();
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void start() {
                lastTime = System.nanoTime();
                super.start();
            }

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) * 1e-9;
                controller.update(deltaTime);

                int camera = controller.getGhostX();

                // Scrolling background
                int backgroundOffset = camera % STAGE_WIDTH;
                context.drawImage(new Image("./images/bg.png"), 0 - backgroundOffset, 0);
                context.drawImage(new Image("./images/bg.png"), STAGE_WIDTH - backgroundOffset, 0);

                // Drawing entities
                int[][] entityCoordinates = controller.getEntityCoordinates();
                for (int i = 0; i < entityCoordinates.length; i++) {
                    int imageNum = entityCoordinates[i][0];
                    Image image = new Image("./images/entities/" + imageNum + ".png");
                    int x = (entityCoordinates[i][1] - camera) + STAGE_WIDTH / 2;
                    int y = entityCoordinates[i][2];
                    int r = entityCoordinates[i][3];
                    context.drawImage(image, x - r, y - r, r * 2, r * 2);
                }

                lastTime = now;
            }
        };

        timer.start();

        // Menu bar

        // Stage set up
        primaryStage.setTitle("Flappy Ghost");
        primaryStage.setScene(gameScene); // change to startScene when that's implemented
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

// TODO: Menu bar (pausing & debug mode)
// TODO: Implement starting screen
// TODO: Implement restart (pause 3 seconds, then reset everything?)
// TODO: Bonus obstacles
// TODO: Bonus secret code
// TODO: Bonus Android port
