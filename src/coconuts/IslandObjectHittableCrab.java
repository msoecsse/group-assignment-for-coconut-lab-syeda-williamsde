package coconuts;

import javafx.scene.image.Image;

// Represents the object that shoots down coconuts but can be hit by coconuts. Killing the
//   crab ends the game
// This is a domain class; other than Image, do not introduce JavaFX or other GUI components here
public class IslandObjectHittableCrab extends AbstractIslandObjectHittable {
    private static final int WIDTH = 50; // assumption: height and width are the same
    private static final Image crabImage = new Image("file:images/crab-1.png");

    public IslandObjectHittableCrab(OhCoconutsGameManager game, int skyHeight, int islandWidth) {
        super(game, islandWidth / 2, skyHeight, WIDTH, crabImage);
    }

    @Override
    public void step() {
        // do nothing
    }

    // Captures the crab crawling sideways
    public void crawl(int offset) {
        x += offset;
        display();
    }

    @Override
    public boolean isGroundObject() {
        return true;
    }

    @Override
    public boolean isTouching(AbstractIslandObject other) {
        if (!other.isFalling() || other.isGroundObject()) return false;

        int crabLeft = this.x;
        int crabRight = this.x + this.width;
        int crabTop = this.y;
        int crabBottom = this.y + this.width;


        int otherLeft = other.x - other.width;
        int otherRight = other.x + other.width;
        int otherTop = other.y;
        int otherBottom = other.y + other.width;

        boolean horizontalOverlap = crabRight >= otherLeft && crabLeft <= otherRight;
        boolean verticalOverlap = crabTop <= otherBottom && crabBottom >= otherTop;

        return horizontalOverlap && verticalOverlap;
    }

    @Override
    public boolean canHit(AbstractIslandObject other) {
        return other.isFalling();
    }
}
