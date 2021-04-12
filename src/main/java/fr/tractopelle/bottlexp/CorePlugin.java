package fr.tractopelle.bottlexp;

import fr.tractopelle.bottlexp.command.BottleXPCommand;
import fr.tractopelle.bottlexp.config.Config;
import fr.tractopelle.bottlexp.listener.BottleXPListener;
import fr.tractopelle.bottlexp.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {

    private Config configuration;
    private final Logger log = new Logger(this.getDescription().getFullName());

    @Override
    public void onEnable() {

        init();

    }

    public void init(){

        registerListeners();

        registerCommands();

        this.configuration = new Config(this, "config");


        log.info("=======================================", Logger.LogType.SUCCESS);
        log.info(" Plugin initialization in progress...", Logger.LogType.SUCCESS);
        log.info(" Author: Tractopelle#4020", Logger.LogType.SUCCESS);
        log.info("=======================================", Logger.LogType.SUCCESS);
    }

    private void registerCommands() {
        new BottleXPCommand(this);
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BottleXPListener(this), this);

    }

    public Config getConfiguration() {
        return configuration;
    }
}
