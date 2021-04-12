package fr.tractopelle.bottlexp.utils.command;

import fr.tractopelle.bottlexp.CorePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BCommand implements CommandExecutor {

    private final String commandName;
    private final boolean consoleCanExecute;
    private final CorePlugin corePlugin;

    public BCommand(CorePlugin corePlugin, String commandName, boolean consoleCanExecute) {
        this.commandName = commandName;
        this.consoleCanExecute = consoleCanExecute;
        this.corePlugin = corePlugin;
        corePlugin.getCommand(commandName).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getLabel().equalsIgnoreCase(commandName))
            return true;

        if (!consoleCanExecute && !(sender instanceof Player)) {
            sender.sendMessage(corePlugin.getConfiguration().getString("NOT-PLAYER"));
            return true;
        }

        return execute(sender, args);
    }

    public abstract boolean execute(CommandSender sender, String[] args);
}