package fr.volax.warenplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin pour Waren dans le cadre d'une vidéo
 * @author Volax
 */
public class PluginMain extends JavaPlugin implements Listener {
    protected boolean isActivate = false;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§aLancement du plugin...");

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getPluginCommand("start-event").setExecutor((sender, a, b, c) -> {
            this.isActivate = true;
            Bukkit.broadcastMessage("§aActivation de l'event...");
            return false;
        });

        Bukkit.getPluginCommand("stop-event").setExecutor((sender, a, b, c) -> {
            this.isActivate = false;
            Bukkit.broadcastMessage("§cDésactivation de l'event...");
            return false;
        });

        Bukkit.getPluginCommand("reset").setExecutor((sender, a, b, c) -> {
            if(!(sender instanceof Player)) return false;

            ((Player) sender).setHealthScale(22.0D);
            ((Player) sender).setHealthScale(20.0D);
            ((Player) sender).setMaxHealth(20.0D);
            ((Player) sender).setHealth(20.0D);
            sender.sendMessage("§aRemise à §c10 ❤ §a!");
            return false;
        });

        Bukkit.getPluginCommand("show").setExecutor((sender, a, b, c) -> {
            if(sender instanceof Player)
                sender.sendMessage("§aVous avez actuellement §c" + (int) ((Player) sender).getHealthScale() / 2 +" ❤ §a!");
            return false;
        });
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§cArret du plugin...");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(isActivate){
            event.getPlayer().setMaxHealth(event.getPlayer().getMaxHealth() + (getConfig().getInt("demi-coeur")));
            event.getPlayer().setHealthScale(event.getPlayer().getHealthScale() + (getConfig().getInt("demi-coeur")));
            event.getPlayer().setHealth(event.getPlayer().getHealth() + (getConfig().getInt("demi-coeur")));
        }
    }
}