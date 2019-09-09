package fr.feavy.uhcTraining.command;

import fr.feavy.uhcTraining.Training;
import fr.feavy.uhcTraining.player.CustomPlayer;
import fr.feavy.uhcTraining.runnable.TrainingRunnable;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(Training.CHAT_PREFIX + "§cVous devez être opérateur pour pouvoir exécuter cette commande.");
            return true;
        }

        Training.get().broadcast("§c§bDémarrage de la partie...");

        Training.get().broadcast("§aGénération de la map...");

        World world = Bukkit.getServer().createWorld(new WorldCreator(UUID.randomUUID().toString() + "-world"));
        world.setDifficulty(Difficulty.HARD);
        world.setGameRuleValue("naturalGeneration", "false");

        Training.get().broadcast("§aMap générée !");
        Training.get().broadcast("§aTéléportation des joueurs...");

        Training.get().setInvicibilityOn(true);

        for (CustomPlayer customPlayer : Training.get().getPlayerManager().getPlayers()) {
            customPlayer.getPlayer().ifPresent(player -> player.teleport(new Location(world, 0, 128, 0)));
            customPlayer.reset();
        }

        Training.get().broadcast("§aJoueurs téléportés !");

        Training.get().setState(Training.TrainingState.GAME);

        TrainingRunnable trainingRunnable = new TrainingRunnable();
        Training.get().setTrainingRunnable(trainingRunnable);
        trainingRunnable.runTaskTimer(Training.get().getPlugin(), 0L, 20L);


        return true;
    }
}
