import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class QuadTree {
    private int maxDepth;
    private int cap;
    private Node rootNode;

    private void insertRec(Ball ball, Node node, int level) {
        if (!node.isLeaf) {
            if (node.NE.intersects(ball)) {
                insertRec(ball, node.NE, level + 1);
            }
            if (node.NW.intersects(ball)) {
                insertRec(ball, node.NW, level + 1);
            }
            if (node.SE.intersects(ball)) {
                insertRec(ball, node.SE, level + 1);
            }
            if (node.SW.intersects(ball)) {
                insertRec(ball, node.SW, level + 1);
            }
        }
        else {
            node.content.add(ball);
            if (level < maxDepth && node.content.size() > cap) {
                ArrayList<Ball> prevContent = node.subdivideAndGetContent();
                for (Ball ballToMove : prevContent) {
                    insertRec(ballToMove, node, level);
                }
            }
        }
    }

    private ArrayList<Ball> getPossibleCollidersRec(Ball ball, Node node) {
        if (!node.isLeaf) {
            ArrayList<Ball> possibleColliders = new ArrayList<>();
            if (node.NE.intersects(ball)) {
                possibleColliders.addAll(getPossibleCollidersRec(ball, node.NE));
            }
            if (node.NW.intersects(ball)) {
                possibleColliders.addAll(getPossibleCollidersRec(ball, node.NW));
            }
            if (node.SE.intersects(ball)) {
                possibleColliders.addAll(getPossibleCollidersRec(ball, node.SE));
            }
            if (node.SW.intersects(ball)) {
                possibleColliders.addAll(getPossibleCollidersRec(ball, node.SW));
            }

            return possibleColliders;
        }
        else {
            return node.content;
        }
    }

    private void drawRec(GraphicsContext gctx, Node node) {
        if (node.isLeaf) {
            gctx.setStroke(Color.GRAY);
            gctx.strokeRect(node.pos.x, node.pos.y, node.dim.x, node.dim.y);
        }
        else {
            drawRec(gctx, node.NE);
            drawRec(gctx, node.NW);
            drawRec(gctx, node.SE);
            drawRec(gctx, node.SW);
        }
    }

    public QuadTree(int maxDepth, int cap) {
        this.maxDepth = maxDepth;
        this.cap = cap;
        rootNode = new Node(new Vector2D(0, 0), new Vector2D(Demo.WIDTH, Demo.HEIGHT));
    }

    public void insert(Ball ball) {
        insertRec(ball, rootNode, 1);
    }

    public ArrayList<Ball> getPossibleColliders(Ball ball) {
        return getPossibleCollidersRec(ball, rootNode);
    }

    public void draw(GraphicsContext gctx) {
        drawRec(gctx, rootNode);
    }

    private class Node {
        Vector2D pos;
        Vector2D dim;
        Node NE;
        Node NW;
        Node SE;
        Node SW;
        boolean isLeaf;
        ArrayList<Ball> content;

        Node(Vector2D pos, Vector2D dim) {
            this.pos = pos;
            this.dim = dim;
            NE = null;
            NW = null;
            SE = null;
            SW = null;
            isLeaf = true;
            content = new ArrayList<>();
        }

        ArrayList<Ball> subdivideAndGetContent() {
            NE = new Node(new Vector2D(pos.x + dim.x / 2, pos.y), new Vector2D(dim.x / 2, dim.y / 2));
            NW = new Node(new Vector2D(pos.x, pos.y), new Vector2D(dim.x / 2, dim.y / 2));
            SE = new Node(new Vector2D(pos.x + dim.x / 2, pos.y + dim.y / 2), new Vector2D(dim.x / 2, dim.y / 2));
            SW = new Node(new Vector2D(pos.x, pos.y + dim.y / 2), new Vector2D(dim.x / 2, dim.y / 2));
            isLeaf = false;
            ArrayList<Ball> temp = content;
            content = null;
            return temp;
        }

        boolean intersects(Ball ball) {
            return ball.intersects(pos, dim);
        }
    }
}
