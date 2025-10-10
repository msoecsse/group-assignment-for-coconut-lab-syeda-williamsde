package coconuts;

import java.util.ArrayList;
import java.util.List;

// An abstraction of all objects that can be hit by another object
// This captures the Subject side of the Observer pattern; observers of the hit event will take action
//   to process that event
// This is a domain class; do not introduce JavaFX or other GUI components here
public class HitEvent implements HitEventSubject{
    protected final List<HitEventObserver> observers;

    public HitEvent () {
        observers = new ArrayList<>();
    }

    @Override
    public void attach(HitEventObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(HitEventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers(AbstractIslandObjectHittable target, AbstractIslandObject hitter) {
        for (HitEventObserver observer: observers) {
            observer.update(target, hitter);
        }
    }
}
