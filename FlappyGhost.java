package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

public class FlappyGhost extends Application {

    private Stage stage;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.controller = new Controller(this);
        primaryStage.setResizable(false);
        int STAGE_WIDTH = 640;

        // Starting screen

        // Main game
        Pane gameRoot = new Pane();
        Scene gameScene = new Scene(gameRoot, STAGE_WIDTH, 440);
        Canvas canvas = new Canvas(STAGE_WIDTH, 400);
        gameRoot.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();

        // TODO: Animation timer
            // Fetches the "virtual x" coordinate from the Controller, not to be confused with the x of the canvas
            // Draws two parts of the background seamlessly
            // From a list of coordinates, draws all of the entities

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

                // Scrolling background
                int backgroundOffset = controller.getGhostX() % 640;
                context.drawImage(new Image("./images/bg.png"), 0 - backgroundOffset, 0);
                context.drawImage(new Image("./images/bg.png"), 640 - backgroundOffset, 0);

                lastTime = now;
            }
        };

        timer.start();

        // Ending screen

        // Stage set up
        primaryStage.setTitle("Flappy Ghost");
        primaryStage.setScene(gameScene); // change to startScene when that's implemented
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

// TODO: Scrolling background
// TODO: Ghost jumping & physics
// TODO: Implement obstacles
// TODO: End game
// TODO: Implement MVC!
// TODO: UI
// TODO: Debug mode
// TODO: Bonus obstacles
// TODO: Bonus secret code
// TODO: Bonus Android port

// TODO: Learn how to transition between scenes
// TODO: Implement all the scenes
