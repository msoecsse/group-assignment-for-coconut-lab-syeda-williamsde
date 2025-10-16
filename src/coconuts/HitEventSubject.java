/*
 * Course: SWE2410-121
 * Fall 2025-2026
 * File header contains class HitEventSubject
 * Name: Ameera
 * Created 10/9/2025
 */
package coconuts;

/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Interface HitEventSubject Purpose: This is the Observer Pattern structure
 *
 * @author Ameera
 * @version created on 10/9/2025 11:42 AM
 */
public interface HitEventSubject {
    /**
     * Adds observer to list
     * @param observer - observer to add
     */
    void attach(HitEventObserver observer);

    /**
     * Removes observer from list
     * @param observer - observer to remove
     */
    void detach(HitEventObserver observer);

    /**
     * Notifies all observers after event
     * Parameters are used to determine objects when touching
     * @param target - target
     * @param hitter - hitter
     */
    void notifyAllObservers(AbstractIslandObjectHittable target, AbstractIslandObject hitter);
}