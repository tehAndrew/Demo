/* ArenaSettings
*  Struct like class that stores Arena generation parameters.
*/
public class ArenaSettings {
    public int ballAmount;
    public double minRadius;
    public double maxRadius;
    public double minVel;
    public double maxVel;
    public double spawnRectX;
    public double spawnRectY;
    public double spawnRectW;
    public double spawnRectH;
    public boolean usingQuadTree;
    public boolean drawingQuadTree;
    public int maxDepth;
    public int cap;

    public ArenaSettings() {
        ballAmount = 0;
        minRadius = 0.0;
        maxRadius = 0.0;
        minVel = 0.0;
        maxVel = 0.0;
        spawnRectX = 0.0;
        spawnRectY = 0.0;
        spawnRectW = 0.0;
        spawnRectH = 0.0;
        usingQuadTree = false;
        drawingQuadTree = false;
        maxDepth = 0;
        cap = 0;
    }
}
