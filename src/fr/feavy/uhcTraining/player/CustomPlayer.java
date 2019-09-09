package fr.feavy.uhcTraining.player;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class CustomPlayer {
    private UUID uuid;

    public CustomPlayer(@NotNull Player player) {
        this.uuid = player.getUniqueId();
    }

    public CustomPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }

    public UUID getUUID() {
        return uuid;
    }

    public void reset() {
        getPlayer().ifPresent(player -> {
            player.setFoodLevel(20);
            player.setSaturation(20f);
            player.setHealth(20);
            player.setFireTicks(0);
            player.getInventory().clear();
            player.getActivePotionEffects().clear();
        });
    }
}
