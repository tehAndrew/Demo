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
    // Dimensions of the world and window
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;

    // Jerry settings
    final int BALL_AMOUNT = 4000;
    final double MIN_RADIUS = 5.0;
    final double MAX_RADIUS = 10.0;
    final double MIN_VEL = 1;
    final double MAX_VEL = 3;

    // Jerry spawn settings
    final double SPAWN_RECT_X = 0.0;
    final double SPAWN_RECT_Y = 300.0;
    final double SPAWN_RECT_W = WIDTH;
    final double SPAWN_RECT_H = HEIGHT - 600.0;

    // Quad tree settings
    final boolean USE_QUAD_TREE = true;
    final boolean DRAW_QUAD_TREE = true;
    final int MAX_DEPTH = 7;
    final int CAP = 15;

    final String TITLE = "QuadTree Demo";

    Arena arena;
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
        arena = null;
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

        // Init Arena
        ArenaSettings as = new ArenaSettings();
        as.ballAmount = BALL_AMOUNT;
        as.minRadius = MIN_RADIUS;
        as.maxRadius = MAX_RADIUS;
        as.minVel = MIN_VEL;
        as.maxVel = MAX_VEL;
        as.spawnRectX = SPAWN_RECT_X;
        as.spawnRectY = SPAWN_RECT_Y;
        as.spawnRectH = SPAWN_RECT_H;
        as.spawnRectW = SPAWN_RECT_W;
        as.usingQuadTree = USE_QUAD_TREE;
        as.drawingQuadTree = DRAW_QUAD_TREE;
        as.maxDepth = MAX_DEPTH;
        as.cap = CAP;

        arena = new Arena(as);

        // Animation timer will try to update 60 times per second
        prevTimeStamp = System.nanoTime() / 1000000000.0;
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                timeStamp = currentNanoTime / 1000000000.0;
                elapsedTime = timeStamp - prevTimeStamp;
                prevTimeStamp = timeStamp;

                fpsTime += elapsedTime;
                if (fpsTime >= 0.2) {
                    // 1 / 60 = Optimal update time. Thus 1 / elapsedTime = Actual fps.
                    fps = 1 / elapsedTime;
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
