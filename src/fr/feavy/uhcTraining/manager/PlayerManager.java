package fr.feavy.uhcTraining.manager;

import fr.feavy.uhcTraining.player.CustomPlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class PlayerManager {
    private final Map<UUID, CustomPlayer> playerMap = new HashMap<>();

    public void loadPlayers(FileConfiguration config) {
        if(!config.isSet("players"))
            return;

        List<String> playerUUIDs = config.getStringList("players");
        for(String uuidStr : playerUUIDs)
            playerMap.put(UUID.fromString(uuidStr), new CustomPlayer(UUID.fromString(uuidStr)));
    }

    public void savePlayers(FileConfiguration config) {
        List<String> UUIDs = new ArrayList<>();
        for(UUID uuid : playerMap.keySet())
            UUIDs.add(uuid.toString());
        config.set("players", UUIDs);
    }

    public boolean addPlayer(CustomPlayer player) {
        if(playerMap.containsKey(player.getUUID()))
            return false;

        playerMap.put(player.getUUID(), player);
        return true;
    }

    public boolean removePlayer(CustomPlayer player) {
        return playerMap.remove(player.getUUID()) != null;
    }

    public Optional<CustomPlayer> getPlayer(UUID playerUUID) {
        return Optional.ofNullable(playerMap.get(playerUUID));
    }

    public List<CustomPlayer> getPlayers() {
        return new ArrayList(playerMap.values());
    }

    public boolean isEmpty() {
        return playerMap.isEmpty();
    }
}
