import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Arena {
    private final int MIN_RADIUS = 2;
    private final int MAX_RADIUS = 4;
    private final double MIN_VEL = 0.2;
    private final double MAX_VEL = 1;

    private ArrayList<Ball> balls;
    private QuadTree qt;
    private int ballAmount;
    private int collisionChecks;
    private int collisionsOccuring;

    private void createBalls() {
        Random rand = new Random();
        balls = new ArrayList<>(ballAmount);
        for (int i = 0; i < ballAmount; i++) {
            double radius = MIN_RADIUS + rand.nextDouble() * (MAX_RADIUS - MIN_RADIUS);
            double x = radius + rand.nextDouble() * (Demo.WIDTH - 2 * radius);
            double y = radius + rand.nextDouble() * (Demo.HEIGHT - 2 * radius);
            double vel = MIN_VEL + rand.nextDouble() * (MAX_VEL - MIN_VEL);
            double dir = rand.nextDouble() * 2 * Math.PI;
            balls.add(new Ball(new Vector2D(x, y), radius, vel, dir));
        }
    }

    private void collisionCheck(Ball ball1) {
        for (Ball ball2 : balls) {
            if (ball1 != ball2) {
                collisionChecks++;
                if (ball1.intersects(ball2)) {
                    collisionsOccuring++;
                    ball1.collided();
                }
            }
        }
    }

    private void collisionCheckQuadTree(Ball ball1) {
        ArrayList<Ball> qtBalls = qt.getPossibleColliders(ball1);
        for (Ball ball2 : qtBalls) {
            if (ball1 != ball2) {
                collisionChecks++;
                if (ball1.intersects(ball2)) {
                    collisionsOccuring++;
                    ball1.collided();
                }
            }
        }
    }

    public Arena(int ballAmount) {
        this.ballAmount = ballAmount;
        collisionChecks = 0;
        collisionsOccuring = 0;
        createBalls();
    }

    public int getBallAmount() {
        return ballAmount;
    }

    public int getCollisionChecks() {
        return collisionChecks;
    }

    public int getCollisionsOccuring() {
        return collisionsOccuring;
    }

    public void update() {
        qt = new QuadTree();
        for (Ball ball : balls) {
            qt.insert(ball);
        }

        collisionChecks = 0;
        collisionsOccuring = 0;

        for(Ball ball : balls) {
            ball.update();
        }

        for(Ball ball : balls) {
            collisionCheckQuadTree(ball);
        }
    }

    public void draw(GraphicsContext gctx) {
        qt.draw(gctx);
        for(Ball ball : balls) {
            ball.draw(gctx);
        }
    }
}
