package thirtyvirus.skyblock.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import thirtyvirus.skyblock.helpers.utils;
import thirtyvirus.skyblock.nes;
import thirtyvirus.uber.helpers.Utilities;

public class WandOops implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utilities.warnPlayer(sender, "This command can only be executed by a player!");
            return false;
        }

        Player player = (Player)sender;
        utils.restoreWandOops(player);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        return true;
    }

}
