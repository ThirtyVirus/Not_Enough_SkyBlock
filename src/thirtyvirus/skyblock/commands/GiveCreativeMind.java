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

public class GiveCreativeMind implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // EXAMPLE: /givecreativemind VirusCam &c[&fYOUTUBE&c],thirtyvirus &c[ADMIN],Jayavarmen 1

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

            ItemStack creativeMind = UberItems.getItem("creative_mind").makeItem(1);
            Utilities.storeStringInItem(creativeMind, recipientName, "to");
            Utilities.storeStringInItem(creativeMind, gifterName, "from");
            Utilities.storeIntInItem(creativeMind, edition, "edition");
            UberItems.getItem("creative_mind").updateLore(creativeMind);

            if (recipient != null) Utilities.givePlayerItemSafely(recipient, creativeMind);

        } catch(Exception e) {
            Utilities.warnPlayer(sender, Arrays.asList(
                    UberItems.getPhrase("formatting-error-message"),
                    "/giveCreativeMind recipient recipient_name gifter_name edition (commas are replaced with spaces)"
            ));

        }

        return true;
    }

}
