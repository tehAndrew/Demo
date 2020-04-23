import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball {
    private Vector2D pos;
    private double radius;
    private Vector2D vel;
    private boolean colliding;

    public Ball(Vector2D pos, double radius, double velocity, double direction) {
        this.pos = pos;
        this.radius = radius;
        vel = new Vector2D(velocity * Math.cos(direction), velocity * -Math.sin(direction));
        colliding = false;
    }

    public void collided() {
        colliding = true;
    }

    public boolean intersects(Ball other) {
        double dist = Math.pow(pos.x - other.pos.x, 2) + Math.pow(pos.y - other.pos.y, 2);
        return dist <= Math.pow(radius + other.radius, 2);
    }

    // Check if the ball intersects with a rectangle
    public boolean intersects(Vector2D rectPos, Vector2D rectDim) {
        Vector2D circleDistance = new Vector2D(Math.abs(pos.x - (rectPos.x + rectDim.x / 2)), Math.abs(pos.y - (rectPos.y + rectDim.y / 2)));

        if (circleDistance.x > (rectDim.x / 2 + radius)) {
            return false;
        }
        if (circleDistance.y > (rectDim.y / 2 + radius)) {
            return false;
        }

        if (circleDistance.x <= (rectDim.x / 2)) {
            return true;
        }
        if (circleDistance.y <= (rectDim.x / 2)) {
            return true;
        }

        double cornerDistanceSq = Math.pow(circleDistance.x - rectDim.x / 2, 2) + Math.pow(circleDistance.y - rectDim.y / 2, 2);
        return (cornerDistanceSq <= Math.pow(radius, 2));
    }

    public void update() {
        pos.add(vel);

        if (pos.x + radius > Demo.WIDTH) {
            pos.x = Demo.WIDTH - radius;
            vel.x *= -1;
        }
        else if (pos.x - radius < 0) {
            pos.x = radius;
            vel.x *= -1;
        }

        if (pos.y + radius > Demo.HEIGHT) {
            pos.y = Demo.HEIGHT - radius;
            vel.y *= -1;
        }
        else if (pos.y - radius < 0) {
            pos.y = radius;
            vel.y *= -1;
        }
    }

    public void draw(GraphicsContext gctx) {
        if (colliding) {
            gctx.setFill(Color.rgb(200, 50, 30));
            colliding = false;
        }
        else {
            gctx.setFill(Color.rgb(30, 50, 200));
        }
        gctx.setStroke(Color.BLACK);
        gctx.strokeOval(pos.x - radius, pos.y - radius, radius * 2, radius * 2);
        gctx.fillOval(pos.x - radius, pos.y - radius, radius * 2, radius * 2);
    }
}
