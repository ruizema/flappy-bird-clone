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

/**
 * The Flappy Ghost class .
 */
public class FlappyGhost extends Application {

    private Stage stage;
    private Controller controller;
    private boolean isPaused = false;
    private boolean debugMode = false;

    private int STAGE_WIDTH = 640;
    private int NB_OBSTACLES = 27;

    /**
     * Getter for the width of the stage of the game.
     * @return int value of width
     */
    public int getSTAGE_WIDTH() {
        return STAGE_WIDTH;
    }

    /**
     * Start  method used to create the graphic interface of the game Flappy Ghost. The interface contains a realtime
     * moving background with random fruit images , a moving ghost and a menu section on the bottom of the stage to
     * pause/resume , debug and see the score of the ghost.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.controller = new Controller(this);
        primaryStage.setResizable(false);

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

        // Loading images into memory
        Image[] images = new Image[NB_OBSTACLES + 1];
        for (int i = -1; i < NB_OBSTACLES; i++) {
            images[i + 1] = new Image("./images/entities/" + i + ".png");
        }

        Image bg1 = new Image("./images/bg.png");
        Image bg2 = new Image("./images/bg.png");

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;


            /**
             * The start method is overrided in the animation Timer class. This is used to call the handle method in
             * each frame of the timer created by AnimationTimer.
             */
            @Override
            public void start() {
                lastTime = System.nanoTime();
                super.start();
            }

            /**
             * The handle method is overrided and called every frame of the timer while AnimaionTimer is active is active.
             * This method allows the enities and the game layout to move (background , obstacles,ghost).
             * @param now The timestamp of the current frame given in nanoseconds(long), it will be the same
             *           for all AnimationTimers called during one frame.
             */
            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) * 1e-9;
                controller.update(deltaTime);

                int camera = controller.getGhostX();

                // Scrolling background
                int backgroundOffset = camera % STAGE_WIDTH;
                context.drawImage(bg1, 0 - backgroundOffset, 0);
                context.drawImage(bg2, STAGE_WIDTH - backgroundOffset, 0);

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
                        if (inCollision) {
                            controller.reset();
                            break;
                        }
                        int imageNum = entityCoordinates[i][0];
                        context.drawImage(images[imageNum + 1], x - r, y - r, r * 2, r * 2);
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

    /**
     * The main method of the game Flappy Ghost.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

// TODO: Bonus obstacles
// TODO: Bonus secret code
// TODO: Break up into functions?
// TODO: Bonus Android port
