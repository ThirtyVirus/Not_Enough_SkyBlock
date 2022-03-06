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

public class GiveKloonboat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // EXAMPLE: /givekloonboat VirusCam &c[&fYOUTUBE&c],thirtyvirus &c[ADMIN],Minikloon 1

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

            ItemStack kloonboat = UberItems.getItem("kloonboat").makeItem(1);
            Utilities.storeStringInItem(kloonboat, recipientName, "to");
            Utilities.storeStringInItem(kloonboat, gifterName, "from");
            Utilities.storeIntInItem(kloonboat, edition, "edition");
            UberItems.getItem("kloonboat").updateLore(kloonboat);

            if (recipient != null) Utilities.givePlayerItemSafely(recipient, kloonboat);

        } catch(Exception e) {
            Utilities.warnPlayer(sender, Arrays.asList(
                    UberItems.getPhrase("formatting-error-message"),
                    "/giveKloonboat recipient recipient_name gifter_name edition (commas are replaced with spaces)"
            ));

        }

        return true;
    }

}
