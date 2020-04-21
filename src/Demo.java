import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Demo extends Application {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    final String TITLE = "QuadTree Demo";

    double timeStamp;
    double prevTimeStamp;
    double elapsedTime;
    double fps;

    Ball ball;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        timeStamp = 0;
        prevTimeStamp = 0;
        elapsedTime = 0;
        fps = 0;

        ball = new Ball(new Vector2D(0, 0), 30, 8, -Math.PI / 4);
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

        // Animation timer will try to update 60 times per second
        prevTimeStamp = System.nanoTime() / 1000000000.0;
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                timeStamp = currentNanoTime / 1000000000.0;
                elapsedTime = timeStamp - prevTimeStamp;
                prevTimeStamp = timeStamp;

                // 1 / 60 = Optimal update time. Thus 1 / elapsedTime = Actual fps.
                fps = 1 / elapsedTime;
                System.out.println(fps);

                // update
                ball.update();

                // draw
                gctx.setFill(Color.rgb(20, 20, 20));
                gctx.fillRect(0, 0, WIDTH, HEIGHT);
                ball.draw(gctx);
            }
        }.start();

        stage.show();
    }
}
