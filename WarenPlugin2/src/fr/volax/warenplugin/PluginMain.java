package fr.volax.warenplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PluginMain extends JavaPlugin implements Listener {
    protected boolean isActivate = false;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§aLancement du plugin...");
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();

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
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player)
            if (isActivate)
                event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.getById(new Random().nextInt(32) + 1), (new Random().nextInt(getConfig().getInt("temps-maximum") - getConfig().getInt("temps-minimum") + 1) + getConfig().getInt("temps-minimum")) * 20, 0));
    }
}
