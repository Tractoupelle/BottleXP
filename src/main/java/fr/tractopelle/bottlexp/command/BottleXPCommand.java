package fr.tractopelle.bottlexp.command;

import fr.tractopelle.bottlexp.CorePlugin;
import fr.tractopelle.bottlexp.utils.ItemBuilder;
import fr.tractopelle.bottlexp.utils.command.BCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BottleXPCommand extends BCommand {

    private CorePlugin corePlugin;

    public BottleXPCommand(CorePlugin corePlugin) {
        super(corePlugin, "bottlexp", false);
        this.corePlugin = corePlugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {

        Player player = (Player) commandSender;
        String prefix = corePlugin.getConfiguration().getString("PREFIX");

        if (args.length == 0) {

            final int level = player.getLevel();

            if (level <= 0) {

                player.sendMessage(prefix + corePlugin.getConfiguration().getString("NO-XP"));
                return false;

            }

            if (level < corePlugin.getConfiguration().getInt("MIN-LEVEL")) {

                player.sendMessage(prefix + corePlugin.getConfiguration().getString("MIN-LIMIT-XP").replace("%min%", String.valueOf(corePlugin.getConfiguration().getInt("MIN-LEVEL"))));
                return false;

            }

            ItemBuilder bottleXP = new ItemBuilder(Material.getMaterial(corePlugin.getConfiguration().getString("BOTTLEXP.MATERIAL")))
                    .setName(corePlugin.getConfiguration().getString("BOTTLEXP.NAME").replace("%level%", String.valueOf(level)))
                    .setLore(corePlugin.getConfiguration().getStringList("BOTTLEXP.LORE"));

            player.sendMessage(prefix + corePlugin.getConfiguration().getString("ALL-IN-BOTTLEXP"));
            player.setLevel(0);

            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItem(player.getLocation(), bottleXP.toItemStack());
            } else {
                player.getInventory().addItem(bottleXP.toItemStack());
            }

        } else if (args.length == 1) {

            if(!(isInt(args[0]))){
                player.sendMessage(prefix + corePlugin.getConfiguration().getString("NOT-A-NUMBER"));
                return false;
            }

            final int level = Integer.parseInt(args[0]);

            if (args[0].contains("-") || args[0].contains("+") || args[0].contains("*") || args[0].contains("/")) {
                player.sendMessage(prefix + corePlugin.getConfiguration().getString("NOT-A-NUMBER"));
                return false;
            }

            if (level < corePlugin.getConfiguration().getInt("MIN-LEVEL")) {

                player.sendMessage(prefix + corePlugin.getConfiguration().getString("MIN-LIMIT-XP").replace("%min%", String.valueOf(corePlugin.getConfiguration().getInt("MIN-LEVEL"))));
                return false;

            }

            if(level <= player.getLevel()) {

                ItemBuilder bottleXP = new ItemBuilder(Material.getMaterial(corePlugin.getConfiguration().getString("BOTTLEXP.MATERIAL")))
                        .setName(corePlugin.getConfiguration().getString("BOTTLEXP.NAME").replace("%level%", String.valueOf(level)))
                        .setLore(corePlugin.getConfiguration().getStringList("BOTTLEXP.LORE"));

                player.sendMessage(prefix + corePlugin.getConfiguration().getString("NUMBER-IN-BOTTLEXP").replace("%level%", String.valueOf(level)));
                player.setLevel(player.getLevel() - level);

                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItem(player.getLocation(), bottleXP.toItemStack());
                } else {
                    player.getInventory().addItem(bottleXP.toItemStack());
                }

            } else {

                player.sendMessage(prefix + corePlugin.getConfiguration().getString("NO-XP"));
                return false;

            }

        } else {

            commandSender.sendMessage(prefix + corePlugin.getConfiguration().getString("USAGE"));

        }

        return false;
    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
