import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Demo extends Application {
    static final int WIDTH = 640;
    static final int HEIGHT = 480;
    static final String title = "QuadTree Demo";

    private GraphicsContext gctx;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        stage.setTitle(title);

        // Setup scene graph
        Group rootNode = new Group();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);

        // Add canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        rootNode.getChildren().add(canvas);

        // Get graphics context
        gctx = canvas.getGraphicsContext2D();

        stage.show();
    }
}
