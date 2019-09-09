package fr.feavy.uhcTraining;

import fr.feavy.uhcTraining.command.StartCommand;
import fr.feavy.uhcTraining.listener.PlayerListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TrainingPlugin extends JavaPlugin {

    {
        Training.setInstance(new Training(this));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        Training.get().loadFromConfig();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        registerListeners(new PlayerListener());
        getCommand("start").setExecutor(new StartCommand());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Training.get().saveToConfig();
    }

    private void registerListeners(Listener ... listeners) {
        for(Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }
}
