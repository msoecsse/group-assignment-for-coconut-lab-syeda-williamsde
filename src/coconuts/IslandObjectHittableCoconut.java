package coconuts;

import javafx.scene.image.Image;

// Represents the falling object that can kill crabs. If hit by a laser, the coconut disappears
// This is a domain class; other than Image, do not introduce JavaFX or other GUI components here
public class IslandObjectHittableCoconut extends AbstractIslandObjectHittable {
    private static final int WIDTH = 50;
    private static final Image coconutImage = new Image("file:images/coco-1.png");

    public IslandObjectHittableCoconut(OhCoconutsGameManager game, int x) {
        super(game, x, 0, WIDTH, coconutImage);
    }

    @Override
    public void step() {
        y += 5;
    }

    @Override
    public boolean isFalling() {
        return true;
    }

    @Override
    public boolean canHit(AbstractIslandObject other) {
        return other.isGroundObject();
    }

    @Override
    public boolean isTouching(AbstractIslandObject other) {
        if (other.isFalling()) {
            return false;
        }

        int thisLeft = this.x;
        int thisRight = this.x + this.width;
        int thisBottom = this.y + this.width;

        int otherLeft = other.x;
        int otherRight = other.x + other.width;
        int otherTop = other.y;
        int otherBottom = other.y + other.width;

        boolean horizontallyOverlapping =
                        (otherLeft <= thisRight && thisRight <= otherRight) ||
                        (otherLeft <= thisLeft && thisLeft <= otherRight);
        boolean verticallyOverlapping = (otherTop <= thisBottom && thisBottom <= otherBottom);

        return horizontallyOverlapping && verticallyOverlapping;
    }
}
