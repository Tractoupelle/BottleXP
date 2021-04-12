package fr.tractopelle.bottlexp.listener;

import fr.tractopelle.bottlexp.CorePlugin;
import fr.tractopelle.bottlexp.utils.Logger;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class BottleXPListener implements Listener {

    private CorePlugin corePlugin;

    public BottleXPListener(CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    @EventHandler
    public void onBottleClick(ProjectileLaunchEvent event){

        if(event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof ThrownExpBottle) {

            final Player player = (Player) event.getEntity().getShooter();

            if (player.getItemInHand().hasItemMeta()
                    && player.getItemInHand().getItemMeta().hasDisplayName()
                    && player.getItemInHand().getItemMeta().getDisplayName().startsWith(corePlugin.getConfiguration().getString("BOTTLEXP.NAME").replace("%level%", ""))) {

                event.setCancelled(true);

                String remplacement = player.getItemInHand().getItemMeta().getDisplayName().replace(corePlugin.getConfiguration().getString("BOTTLEXP.NAME").replace("%level%", ""), "");

                final int level = Integer.parseInt(remplacement);

                if (level <= 0) {
                    return;
                }

                player.sendMessage(corePlugin.getConfiguration().getString("PREFIX") + corePlugin.getConfiguration().getString("RECIEVE-XP").replace("%level%", String.valueOf(level)));

                player.setLevel(player.getLevel() + level);

                try {
                    player.playSound(player.getLocation(), Sound.valueOf(corePlugin.getConfiguration().getString("BOTTLEXP.SOUND")), 5F, 5F);
                } catch(Exception e) {
                    Logger.info("Error: The sound put in the config is invalid", Logger.LogType.ERROR);
                }

            }
        }
    }
}
