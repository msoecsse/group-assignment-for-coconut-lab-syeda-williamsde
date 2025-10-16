/*
 * Course: SWE2410-121
 * Fall 2025-2026
 * Name: Demarion Williams , Ameera Syed
 * Created 10/15/2025
 */
package coconuts;

import javafx.scene.image.Image;
import java.util.LinkedList;
import java.util.List;

/**
 * Crab that can be hit by coconuts. Implements the Observer Pattern so that
 * other objects (like the scoreboard or game manager) can respond when the crab is hit.
 * Modified by Ameera & Demarion
 */
public class IslandObjectHittableCrab extends AbstractIslandObjectHittable implements HitEventSubject {
    private static final int WIDTH = 50; // assumption: height and width are the same
    private static final Image crabImage = new Image("file:images/crab-1.png");

    // List of observers for the observer pattern
    private final List<HitEventObserver> observers = new LinkedList<>();

    public IslandObjectHittableCrab(OhCoconutsGameManager game, int skyHeight, int islandWidth) {
        super(game, islandWidth / 2, skyHeight, WIDTH, crabImage);
    }

    @Override
    public void step() {
        // Crab does not move automatically
    }

    /**
     * Move the crab sideways.
     * @param offset positive for right, negative for left
     */
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

        int otherLeft = other.x;
        int otherRight = other.x + other.width;
        int otherTop = other.y;
        int otherBottom = other.y + other.width;

        boolean horizontalOverlap = crabRight >= otherLeft && crabLeft <= otherRight;
        boolean verticalOverlap = crabTop <= otherBottom && crabBottom >= otherTop;

        boolean touching = horizontalOverlap && verticalOverlap;

        // Notify observers if the crab is hit
        if (touching) {
            notifyAllObservers(this, other);
        }

        return touching;
    }

    @Override
    public boolean canHit(AbstractIslandObject other) {
        // Crab is only hit by falling objects
        return other.isFalling();
    }

    // ===== Observer Pattern Implementation =====

    @Override
    public void attach(HitEventObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(HitEventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers(AbstractIslandObjectHittable target, AbstractIslandObject hitter) {
        for (HitEventObserver observer : observers) {
            observer.update(target, hitter);
        }
    }
}
