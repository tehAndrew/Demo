import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Demo extends Application {
    final int WIDTH = 640;
    final int HEIGHT = 480;
    final String TITLE = "QuadTree Demo";

    double timeStamp;
    double prevTimeStamp;
    double elapsedTime;
    double fps;

    double x = 0;

    public static void main(String[] args) {
        launch(args);
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
                // draw
            }
        }.start();

        stage.show();
    }
}
