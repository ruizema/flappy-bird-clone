package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FlappyGhost extends Application {

    private Stage stage;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.controller = new Controller(this);
        primaryStage.setResizable(false);

        // Starting screen

        // Main game
        Pane gameRoot = new Pane();
        Scene gameScene = new Scene(gameRoot, 640, 440);
        Canvas canvas = new Canvas(300, 275);
        GraphicsContext context = canvas.getGraphicsContext2D();

        gameRoot.getChildren().add(canvas);

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
