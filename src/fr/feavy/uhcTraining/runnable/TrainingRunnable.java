package fr.feavy.uhcTraining.runnable;

import fr.feavy.uhcTraining.Training;
import fr.feavy.uhcTraining.settings.Settings;
import org.bukkit.scheduler.BukkitRunnable;

public class TrainingRunnable extends BukkitRunnable {
    private int secondsTimer = 0;

    @Override
    public void run() {
        secondsTimer++;
        if(secondsTimer == Settings.INVICIBILITY_DURATION_SECONDS) {
            Training.get().setInvicibilityOn(false);
        }
    }
}
