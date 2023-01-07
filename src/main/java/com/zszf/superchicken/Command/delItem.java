package com.zszf.superchicken.Command;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import static com.zszf.superchicken.Superchicken.instance;

public class delItem implements CommandExecutor {

    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int sum = 0;
        if (args.length != 0) {
            return false;
        }
        try {
            Player sender_player = (Player) sender;
            World sender_world = sender_player.getWorld();
            for (int i = 0; i < sender_world.getEntities().size(); i++) {
                if (sender_world.getEntities().get(i).getType() == EntityType.DROPPED_ITEM) {
                    sender_world.getEntities().get(i).remove();
                    sum++;
                }
            }
            instance.getLogger().info("删除了" + sum + "个掉落物！");
            sender_player.sendMessage("删除了" + sum + "个掉落物");
        } catch (ClassCastException evt) {
            for (World world : instance.getServer().getWorlds()) {
                for (Entity entities : world.getEntities()) {
                    if (entities.getType() == EntityType.DROPPED_ITEM) {
                        entities.remove();
                        sum++;
                    }
                }
            }
            instance.getLogger().info("删除了" + sum + "个掉落物！");
        }
        return true;
    }
}
