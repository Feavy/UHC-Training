package fr.feavy.uhcTraining.listener;

import fr.feavy.uhcTraining.Training;
import fr.feavy.uhcTraining.player.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");

        if (Bukkit.getOnlinePlayers().size() == 1 && Training.get().getState() == Training.TrainingState.BEFORE) {
            Training.get().showWelcomeMessage();
        }

        boolean connectedBefore = !Training.get().getPlayerManager().addPlayer(new CustomPlayer(e.getPlayer()));

        if (Training.get().getState() == Training.TrainingState.GAME) {
            if (connectedBefore)
                Training.get().broadcast("§c" + e.getPlayer().getName() + " §areprend l'entraînement.");
            else
                Training.get().broadcast("§c" + e.getPlayer().getName() + " §rejoint l'entraînement.");
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player && Training.get().isInvicibilityOn())
            e.setCancelled(true);
    }
}
