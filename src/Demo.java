import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Demo extends Application {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    final String TITLE = "QuadTree Demo";

    double timeStamp;
    double prevTimeStamp;
    double elapsedTime;
    double fpsTime;
    double fps;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        timeStamp = 0;
        prevTimeStamp = 0;
        elapsedTime = 0;
        fpsTime = 0;
        fps = 0;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);

        // Setup scene graph
        Group rootNode = new Group();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);

        // Add canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        rootNode.getChildren().add(canvas);

        // Get graphics context
        GraphicsContext gctx = canvas.getGraphicsContext2D();

        Arena arena = new Arena(2000);

        // Animation timer will try to update 60 times per second
        prevTimeStamp = System.nanoTime() / 1000000000.0;
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                timeStamp = currentNanoTime / 1000000000.0;
                elapsedTime = timeStamp - prevTimeStamp;
                prevTimeStamp = timeStamp;

                fpsTime += elapsedTime;
                if (fpsTime >= 0.2) {
                    // 1 / 60 = Optimal update time. Thus 1 / elapsedTime = Actual fps.
                    fps = 1 / elapsedTime;
                    System.out.println(fps);
                    fpsTime = 0;
                }

                // update
                arena.update();

                // draw
                gctx.setFill(Color.rgb(20, 20, 20));
                gctx.fillRect(0, 0, WIDTH, HEIGHT);
                arena.draw(gctx);

                gctx.setFill(Color.rgb(40, 40, 40, 0.5));
                gctx.fillRect(0, 0, 400, 120);

                gctx.setFont(new Font(20));
                gctx.setFill(Color.rgb(240, 240, 240));
                gctx.fillText("FPS: " + Integer.toString((int) fps), 10, 20);
                gctx.fillText("Amount of balls: " + Integer.toString(arena.getBallAmount()), 10, 50);
                gctx.fillText("Amount of collision checks: " + Integer.toString(arena.getCollisionChecks()), 10, 80);
                gctx.fillText("Amount of collisions: " + Integer.toString(arena.getCollisionsOccuring()), 10, 110);
            }
        }.start();

        stage.show();
    }
}
