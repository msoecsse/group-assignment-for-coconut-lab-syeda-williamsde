/*
 * Course: SWE2410-121
 * Fall 2025-2026
 * File header contains class HitEventObserver
 * Name: Ameera
 * Created 10/9/2025
 */
package coconuts;

/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Interface HitEventObserver Purpose: Updates for the observer pattern
 *
 * @author Ameera
 * @version created on 10/9/2025 11:43 AM
 */
public interface HitEventObserver {
    void update(AbstractIslandObjectHittable target, AbstractIslandObject hitter);
}