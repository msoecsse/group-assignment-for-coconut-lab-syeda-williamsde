package coconuts;

import javafx.scene.image.Image;

// Represents the beam of light moving from the crab to a coconut; can hit only falling objects
// This is a domain class; do not introduce JavaFX or other GUI components here
// Modified by Ameera
public class IslandObjectLaserBeam extends AbstractIslandObject {
    private static final int WIDTH = 5; // must be updated with image
    private static final Image laserImage = new Image("file:images/laser-1.png");

    public IslandObjectLaserBeam(OhCoconutsGameManager game, int eyeHeight, int crabCenterX) {
        super(game, crabCenterX - WIDTH / 2, eyeHeight, WIDTH, laserImage);
    }

    public int hittable_height() {
        return y + WIDTH;
    }

    @Override
    public void step() {
        y -= 5;
    }

    @Override
    public boolean isTouching(AbstractIslandObject other) {
        if (!other.isHittable() || other.isGroundObject()) return false;

        // Laser bounding box
        int laserLeft = this.x;
        int laserRight = this.x + this.width;
        int laserTop = this.y;
        int laserBottom = this.y + this.width;

        // Other object bounding box
        int otherLeft = other.x;
        int otherRight = other.x + other.width;
        int otherTop = other.y;
        int otherBottom = other.y + other.width; // assume square image

        boolean horizontalOverlap = laserRight >= otherLeft && laserLeft <= otherRight;
        boolean verticalOverlap = laserBottom >= otherTop && laserTop <= otherBottom;

        return horizontalOverlap && verticalOverlap;
    }

    @Override
    public boolean canHit(AbstractIslandObject other) {
        return other.isFalling();
    }
}
