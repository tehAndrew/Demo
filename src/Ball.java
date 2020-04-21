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
        gctx.fillOval(pos.x - radius, pos.y - radius, radius * 2, radius * 2);
    }
}
