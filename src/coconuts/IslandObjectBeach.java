package coconuts;

// the beach catches (hits) coconuts and increases the coconut score
// This is a domain class; do not introduce JavaFX or other GUI components here
// Modified by Ameera
public class IslandObjectBeach extends AbstractIslandObject {

    public IslandObjectBeach(OhCoconutsGameManager game, int skyHeight, int islandWidth) {
        super(game, 0, skyHeight, islandWidth, null);
    }

    @Override
    public void step() { /* do nothing */ }

    @Override
    public boolean isTouching(AbstractIslandObject other) {
        if (!other.isFalling()) return false;

        int otherTopY = other.y;
        return otherTopY >= this.y;
    }

    @Override
    public boolean isGroundObject() {
        return true;
    }

    @Override
    public boolean canHit(AbstractIslandObject other) {
        return other.isFalling();
    }
}
