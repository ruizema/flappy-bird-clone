package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FlappyGhost extends Application {

    private Stage stage;
    private Controller controller;
    private boolean isPaused = false;
    private boolean debugMode = false;

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
        VBox gameRoot = new VBox();
        Scene gameScene = new Scene(gameRoot, STAGE_WIDTH, 440);
        Canvas canvas = new Canvas(STAGE_WIDTH, 400);
        gameRoot.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();

        gameScene.setOnMouseClicked((event) -> {
            canvas.requestFocus();
        });

        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                controller.jump();
            }
        });

        Text score = new Text();

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
                    int x = (entityCoordinates[i][1] - camera) + STAGE_WIDTH / 2;
                    int y = entityCoordinates[i][2];
                    int r = entityCoordinates[i][3];
                    boolean inCollision = entityCoordinates[i][4] == 1;
                    if (debugMode) {
                        if (i == 0) {
                            context.setFill(Color.BLACK);
                        } else if (inCollision) {
                            context.setFill(Color.RED);
                        } else {
                            context.setFill(Color.YELLOW);
                        }
                        context.fillOval(x - r, y - r, r * 2, r * 2);
                    } else {
                        int imageNum = entityCoordinates[i][0];
                        Image image = new Image("./images/entities/" + imageNum + ".png");
                        context.drawImage(image, x - r, y - r, r * 2, r * 2);
                    }
                }

                lastTime = now;

                score.setText("Score : " + controller.getGhostScore());

                Platform.runLater(() -> {
                    canvas.requestFocus();
                });
            }
        };

        timer.start();

        // Menu bar
        HBox menu = new HBox();

        Button pause = new Button("Pause");
        pause.setOnAction((event) -> {
            if (!isPaused) {
                pause.setText("Resume");
                isPaused = true;
                timer.stop();
            } else {
                pause.setText("Pause");
                isPaused = false;
                timer.start();
            }
        });

        CheckBox debug = new CheckBox("Mode Debug");
        debug.setOnAction((event) -> {
            debugMode = debug.isSelected();
        });

        Separator sep1 = new Separator();
        Separator sep2 = new Separator();
        sep1.setOrientation(Orientation.VERTICAL);
        sep2.setOrientation(Orientation.VERTICAL);

        menu.getChildren().addAll(pause, sep1, debug, sep2, score);
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        menu.setPadding(new Insets(8));
        gameRoot.getChildren().add(menu);

        // Stage set up
        primaryStage.setTitle("Flappy Ghost");
        primaryStage.setScene(gameScene); // change to startScene when that's implemented
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

// TODO: Implement starting screen
// TODO: Implement restart (pause 3 seconds, then reset everything?)
// TODO: Bonus obstacles
// TODO: Bonus secret code
// TODO: Bonus Android port
