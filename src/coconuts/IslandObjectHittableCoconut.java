/*
 * Course: SWE2410-121
 * Fall 2025-2026
 * Name: Demarion Williams , Syeda
 * Created 10/15/2025
 */
package coconuts;

import javafx.scene.image.Image;
import java.util.LinkedList;
import java.util.List;

/**
 * Falling coconut that can hit ground objects like the crab or beach.
 * Implements the HitEventSubject interface for the Observer Pattern.
 */
public class IslandObjectHittableCoconut extends AbstractIslandObjectHittable implements HitEventSubject {
    private static final int WIDTH = 50;
    private static final Image coconutImage = new Image("file:images/coco-1.png");

    // List of observers
    private final List<HitEventObserver> observers = new LinkedList<>();

    public IslandObjectHittableCoconut(OhCoconutsGameManager game, int x) {
        super(game, x, 0, WIDTH, coconutImage);
    }

    @Override
    public void step() {
        y += 5; // coconut falls down
        display();
    }

    @Override
    public boolean isFalling() {
        return true;
    }

    @Override
    public boolean canHit(AbstractIslandObject other) {
        // Coconut can hit ground objects like crab or beach
        return other.isGroundObject();
    }

    @Override
    public boolean isTouching(AbstractIslandObject other) {
        if (!canHit(other)) return false;

        int thisLeft = x;
        int thisRight = x + width;
        int thisBottom = y + width;

        int otherLeft = other.x;
        int otherRight = other.x + other.width;
        int otherTop = other.y;
        int otherBottom = other.y + other.width;

        boolean horizontallyOverlapping =
                (otherLeft <= thisRight && thisRight <= otherRight) ||
                        (otherLeft <= thisLeft && thisLeft <= otherRight);
        boolean verticallyOverlapping = (otherTop <= thisBottom && thisBottom <= otherBottom);

        boolean touching = horizontallyOverlapping && verticallyOverlapping;

        // Notify observers when this coconut hits another object
        if (touching) {
            notifyAllObservers((AbstractIslandObjectHittable) other, this);
        }

        return touching;
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
