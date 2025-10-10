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
    void attach(HitEventObserver observer);
    void detach(HitEventObserver observer);
    void notifyAllObservers(AbstractIslandObjectHittable target, AbstractIslandObject hitter);
}