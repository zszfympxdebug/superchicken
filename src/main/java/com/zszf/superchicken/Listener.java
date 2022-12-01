package com.zszf.superchicken;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

import static com.zszf.superchicken.Superchicken.instance;

public class Listener implements org.bukkit.event.Listener {

    private static final int dupeNum = instance.getConfig().getInt("dupeNum");
    private static Map<UUID, Long> playerCDMap = new HashMap<>();

    public Listener() {
    }

    @EventHandler
    @ParametersAreNonnullByDefault
    public void superChicken(PlayerInteractEntityEvent evt) {
        List<String> enablePlayersString = (List<String>) instance.getConfig().getList("enableChickenDupePlayers");
        try{
            Long playerLastDupeTime = playerCDMap.get(evt.getPlayer().getUniqueId());
            if(playerLastDupeTime + instance.getConfig().getLong("chickenDupeCD") < System.currentTimeMillis()){
                playerCDMap.put(evt.getPlayer().getUniqueId(), System.currentTimeMillis());
            }else {
                evt.getPlayer().sendMessage("你的鸡刷正在冷却！");
                return;
            }
        } catch (NullPointerException e){
            playerCDMap.put(evt.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
        if (Superchicken.enableSuperChicken) {
            for (String enablePlayersStr : enablePlayersString) {
                if (Objects.equals(enablePlayersStr, "all")) {
                    if (evt.getRightClicked().getType().equals(EntityType.CHICKEN)) {
                        for (int i = 0; i < dupeNum; ++i) {
                            evt.getRightClicked().setCustomName(evt.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                            Item item = (Item) evt.getRightClicked().getWorld().spawnEntity(evt.getRightClicked().getLocation(), EntityType.DROPPED_ITEM);
                            item.setItemStack(evt.getPlayer().getInventory().getItemInMainHand());
                        }
                        instance.getLogger().info(evt.getPlayer().getDisplayName() + " dupe once");
                        for (Player ops : instance.getServer().getOnlinePlayers()) {
                            if (ops.isOp()) {
                                ops.sendMessage(evt.getPlayer().getDisplayName() + " dupe once");
                            }
                        }

                    }
                } else if (Objects.equals(enablePlayersStr, evt.getPlayer().getDisplayName())) {
                    if (evt.getRightClicked().getType().equals(EntityType.CHICKEN)) {
                        for (int i = 0; i < dupeNum; ++i) {
                            evt.getRightClicked().setCustomName(evt.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                            Item item = (Item) evt.getRightClicked().getWorld().spawnEntity(evt.getRightClicked().getLocation(), EntityType.DROPPED_ITEM);
                            item.setItemStack(evt.getPlayer().getInventory().getItemInMainHand());
                        }
                        instance.getLogger().info(evt.getPlayer().getDisplayName() + " dupe once");
                        for (Player ops : instance.getServer().getOnlinePlayers()) {
                            if (ops.isOp()) {
                                ops.sendMessage(evt.getPlayer().getDisplayName() + " dupe once");
                            }
                        }
                    }
                } else {
                    evt.getPlayer().sendMessage("你没有权限来使用鸡刷！");
                }
            }
        }
    }

    @EventHandler
    @ParametersAreNonnullByDefault
    public void TNTDupe(BlockPlaceEvent evt) {
        if (Objects.equals(evt.getItemInHand().getType(), new ItemStack(Material.TNT).getType())) {
            evt.getPlayer().getInventory().getItemInMainHand().setAmount(1);
        }
    }
}
