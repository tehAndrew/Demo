import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Arena {
    private ArenaSettings as;
    private ArrayList<Ball> balls;
    private QuadTree qt;
    private int collisionChecks;
    private int collisionsOccuring;

    private void createBalls() {
        Random rand = new Random();
        balls = new ArrayList<>(as.ballAmount);
        for (int i = 0; i < as.ballAmount; i++) {
            double radius = as.minRadius + rand.nextDouble() * (as.maxRadius - as.minRadius);
            double x = as.spawnRectX + rand.nextDouble() * (as.spawnRectW);
            double y = as.spawnRectY + rand.nextDouble() * (as.spawnRectH);
            double vel = as.minVel + rand.nextDouble() * (as.maxVel - as.minVel);
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

    public Arena(ArenaSettings as) {
        this.as = as;
        collisionChecks = 0;
        collisionsOccuring = 0;
        createBalls();
    }

    public int getBallAmount() {
        return as.ballAmount;
    }

    public int getCollisionChecks() {
        return collisionChecks;
    }

    public int getCollisionsOccuring() {
        return collisionsOccuring;
    }

    public void update() {
        qt = new QuadTree(as.maxDepth, as.cap);
        for (Ball ball : balls) {
            qt.insert(ball);
        }

        collisionChecks = 0;
        collisionsOccuring = 0;

        for(Ball ball : balls) {
            ball.update();
        }

        for(Ball ball : balls) {
            if (as.usingQuadTree) {
                collisionCheckQuadTree(ball);
            }
            else {
                collisionCheck(ball);
            }
        }
    }

    public void draw(GraphicsContext gctx) {
        if (as.usingQuadTree && as.drawingQuadTree) {
            qt.draw(gctx);
        }
        for(Ball ball : balls) {
            ball.draw(gctx);
        }
    }
}
