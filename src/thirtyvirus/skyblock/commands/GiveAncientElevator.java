package thirtyvirus.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.helpers.Utilities;

import java.util.Arrays;

public class GiveAncientElevator implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // EXAMPLE: /giveancientelevator VirusCam &c[&fYOUTUBE&c],thirtyvirus &c[ADMIN],Minikloon 1 DESCRIPTION

        // verify that the user has proper permissions
        if (!sender.hasPermission("uber.admin")) {
            Utilities.warnPlayer(sender, UberItems.getPhrase("no-permissions-message"));
            return true;
        }

        try {
            Player recipient = Bukkit.getPlayer(args[0]);
            String recipientName = args[1];
            String gifterName = args[2];
            int edition = Integer.parseInt(args[3]);
            StringBuilder description = new StringBuilder();
            for (int counter = 4; counter < args.length; counter++) {
                description.append(args[counter]).append(" ");
            }

            ItemStack ancientElevator = UberItems.getItem("ancient_elevator").makeItem(1);
            Utilities.storeStringInItem(ancientElevator, recipientName, "to");
            Utilities.storeStringInItem(ancientElevator, gifterName, "from");
            Utilities.storeIntInItem(ancientElevator, edition, "edition");
            Utilities.storeStringInItem(ancientElevator, description.toString(), "description");
            UberItems.getItem("ancient_elevator").updateLore(ancientElevator);

            if (recipient != null) Utilities.givePlayerItemSafely(recipient, ancientElevator);

        } catch(Exception e) {
            Utilities.warnPlayer(sender, Arrays.asList(
                    UberItems.getPhrase("formatting-error-message"),
                    "/giveAncientElevator recipient recipient_name gifter_name edition DESCRIPTION (commas are replaced with spaces)"
            ));

        }

        return true;
    }

}
