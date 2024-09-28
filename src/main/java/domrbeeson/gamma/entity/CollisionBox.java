package domrbeeson.gamma.entity;

public class CollisionBox {

    private Pos centre;
    private double horizontalRadius, verticalRadius;
    private double xMin, xMax, yMin, yMax, zMin, zMax;

    public CollisionBox(Pos centre, double horizontalRadius, double verticalRadius) {
        this.centre = centre;
        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
        calculateBounds();
    }

    public void setCentre(Pos centre) {
        this.centre = centre;
        calculateBounds();
    }

    public void setSize(double horizontalRadius, double verticalRadius) {
        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
        calculateBounds();
    }

    private void calculateBounds() {
        xMin = centre.x() - horizontalRadius;
        xMax = centre.x() + horizontalRadius;
        yMin = centre.y() - verticalRadius;
        yMax = centre.y() + verticalRadius;
        zMin = centre.z() - horizontalRadius;
        zMax = centre.z() + horizontalRadius;
    }

    public boolean collides(CollisionBox bb) {
        return ((xMin >= bb.xMin && xMin <= bb.xMax) || (xMax >= bb.xMin && xMax <= bb.xMax))
                && ((yMin >= bb.yMin && yMin <= bb.yMax) || (yMax >= bb.yMin && yMax <= bb.yMax))
                && ((zMin >= bb.zMin && zMin <= bb.zMax) || (zMax >= bb.zMin && zMax <= bb.zMax));
    }

}
