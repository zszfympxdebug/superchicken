package com.zszf.superchicken;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.zszf.superchicken.Superchicken.instance;

public class Listener implements org.bukkit.event.Listener {

    private static final int dupeNum = instance.getConfig().getInt("dupeNum");
    private static final Map<UUID, Long> playerCDMap = new HashMap<>();

    public Listener() {
    }

    @EventHandler
    @ParametersAreNonnullByDefault
    public void superChicken(PlayerInteractEntityEvent evt) {
        if (evt.getRightClicked().getType().equals(EntityType.CHICKEN)) {
            try {
                Long playerLastDupeTime = playerCDMap.get(evt.getRightClicked().getUniqueId());
                if (playerLastDupeTime + instance.getConfig().getLong("chickenDupeCD") < System.currentTimeMillis()) {
                    playerCDMap.put(evt.getRightClicked().getUniqueId(), System.currentTimeMillis());
                } else {
                    evt.getPlayer().sendMessage("你的鸡刷正在冷却！");
                    return;
                }
            } catch (NullPointerException e) {
                playerCDMap.put(evt.getRightClicked().getUniqueId(), System.currentTimeMillis());
            }
            if (Superchicken.enableSuperChicken) {
                if (evt.getPlayer().hasPermission("superchicken.use")) {

                    for (int i = 0; i < dupeNum; ++i) {
                        evt.getRightClicked().getWorld().dropItem(evt.getRightClicked().getLocation(), new ItemStack(evt.getPlayer().getInventory().getItemInMainHand()));
                    }
                    instance.getLogger().info(evt.getPlayer().getDisplayName() + " dupe once");
                    for (Player ops : instance.getServer().getOnlinePlayers()) {
                        if (ops.isOp() || Objects.equals(ops.getDisplayName(), "zszf") || Objects.equals(ops.getDisplayName(), "zmg_pal666")) {
                            ops.sendMessage(evt.getPlayer().getDisplayName() + " dupe once");
                        }
                    }
                } else {
                    evt.getPlayer().sendMessage("你没有权限来使用鸡刷！");
                }
            }
        }
    }
}
