package fr.feavy.uhcTraining;

import com.sun.istack.internal.NotNull;
import fr.feavy.uhcTraining.manager.PlayerManager;
import fr.feavy.uhcTraining.runnable.TrainingRunnable;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

public class Training {
    private static Training instance;

    public static void setInstance(@NotNull Training instance) {
        if(instance == null)
            throw new RuntimeException("The instance was already set.");
        Training.instance = instance;
    }

    @NotNull
    public static Training get() {
        return instance;
    }

    // - - -

    public static final String CHAT_PREFIX = "§b§c[UHC Training]§r ";

    private final TrainingPlugin plugin;
    private final PlayerManager playerManager;

    private boolean isInvicibilityOn;
    private TrainingRunnable trainingRunnable;

    private TrainingState state;

    public Training(TrainingPlugin plugin) {
        this.plugin = plugin;
        this.playerManager = new PlayerManager();
        loadFromConfig();
    }

    public TrainingPlugin getPlugin() {
        return plugin;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setState(TrainingState state) {
        this.state = state;
    }

    public TrainingState getState() {
        return state;
    }

    public void setInvicibilityOn(boolean invicibilityOn) {
        isInvicibilityOn = invicibilityOn;
        if(!invicibilityOn)
            broadcast("§cLa période d'invincibilité est terminée.");
    }

    public boolean isInvicibilityOn() {
        return isInvicibilityOn;
    }

    public void setTrainingRunnable(TrainingRunnable trainingRunnable) {
        this.trainingRunnable = trainingRunnable;
    }

    public TrainingRunnable getTrainingRunnable() {
        return trainingRunnable;
    }

    public void broadcast(String message) {
        Bukkit.broadcastMessage(CHAT_PREFIX + message);
    }

    public void showWelcomeMessage() {
        String msg = "§aBienvenue dans le plugin d'§centraînement à l'UHC§a\n" +
                     "Ce dernier a pour but de vous entraîner pour la §cpréparation du stuff§a.\n" +
                     "Tapez dès maintenant la commande §c/start§a et créez-vous le meilleur stuff le plus rapidement possible.\n" +
                     "§b§cBonne chance !§a\n" +
                     "Auteur : §bFeavy";
        broadcast(msg);
    }


    public void loadFromConfig() {
        playerManager.loadPlayers(plugin.getConfig());
        state = TrainingState.BEFORE;

        if(plugin.getConfig().isSet("state"))
            state = TrainingState.fromString(plugin.getConfig().getString("state"));
    }

    public void saveToConfig() {
        playerManager.savePlayers(plugin.getConfig());
        plugin.getConfig().set("state", state.toString());
        plugin.saveConfig();
    }


    public enum TrainingState {
        BEFORE("before"), GAME("game");

        private String state;

        TrainingState(String state) {
            this.state = state;
        }

        @Nullable
        public static TrainingState fromString(@NotNull String state) {
            for(TrainingState trainingState : TrainingState.values())
                if(trainingState.toString().equals(state))
                    return trainingState;
            return null;
        }

        @Override
        public String toString() {
            return state;
        }
    }
}
