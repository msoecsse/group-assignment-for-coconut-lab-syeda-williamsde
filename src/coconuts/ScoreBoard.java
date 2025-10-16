/*
 * Course: SWE2410-121
 * Fall 2025-2026
 * File header contains class ScoreBoard
 * Name: Ameera Syed
 * Created 10/9/2025
 */
package coconuts;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class ScoreBoard Purpose: Keeps track of destroyed coconuts and coconuts hitting beach
 *
 * @author Ameera Syed
 * @version created on 10/9/2025 1:05 PM
 */
public class ScoreBoard implements HitEventObserver {
    private int coconutsDestroyed;
    private int coconutsHitBeach;
    private final Label destroyedLabel;
    private final Label beachLabel;
    protected HitEvent hitEvent;

    public ScoreBoard(Label destroyedLabel, Label beachLabel) {
        coconutsDestroyed = 0;
        coconutsHitBeach = 0;
        this.destroyedLabel = destroyedLabel;
        this.beachLabel = beachLabel;
        hitEvent = new HitEvent();
        updateLabels();
    }

    @Override
    public void update(AbstractIslandObjectHittable target, AbstractIslandObject hitter) {
        if (hitter.isGroundObject() && target.isFalling()) {
            coconutsHitBeach++;
        } else if (target.isHittable() && !target.isGroundObject()) {
            coconutsDestroyed++;
        }
        Platform.runLater(this::updateLabels);
    }

    /**
     * Updates scoreboard
     */
    private void updateLabels() {
        destroyedLabel.setText("Coconuts destroyed: " + coconutsDestroyed);
        beachLabel.setText("Coconuts hit beach: " + coconutsHitBeach);
    }
}