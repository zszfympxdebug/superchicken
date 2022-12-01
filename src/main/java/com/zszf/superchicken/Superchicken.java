package com.zszf.superchicken;

import com.zszf.superchicken.Command.delItem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Superchicken extends JavaPlugin {

    public static final Logger logger = Bukkit.getLogger();
    public static JavaPlugin instance;
    public static boolean enableSuperChicken;
    public final Configuration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        logger.info("Enable SuperChicken" + getDescription().getVersion());
        enableSuperChicken = config.getBoolean("enableSuperChicken");
        this.getServer().getPluginManager().registerEvents(new Listener(), this);
        Bukkit.getPluginCommand("item").setExecutor(new delItem());

    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Disable SuperChicken");
    }
}
