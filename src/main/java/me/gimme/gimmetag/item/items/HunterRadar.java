package me.gimme.gimmetag.item.items;

import me.gimme.gimmecore.chat.Chat;
import me.gimme.gimmetag.GimmeTag;
import me.gimme.gimmetag.item.AbilityItem;
import me.gimme.gimmetag.item.ItemOngoingUseTaskTimer;
import me.gimme.gimmetag.sfx.SoundEffect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.*;

public class HunterRadar extends AbilityItem {

    private static DecimalFormat df = new DecimalFormat("0.00");
    private static final Material TYPE = Material.CLOCK;
    private static final List<String> LORE = Collections.singletonList("" + ChatColor.ITALIC + ChatColor.YELLOW + "(Right click to activate)");

    private GimmeTag plugin;
    private long durationMillis;
    private int level;

    private Set<UUID> activeItems = new HashSet<>();

    public HunterRadar(double cooldown, boolean consumable, double duration, int level, @NotNull GimmeTag plugin) {
        super(
                "Hunter Radar",
                TYPE,
                true,
                cooldown,
                consumable,
                null
        );

        this.plugin = plugin;
        this.durationMillis = Math.round(duration * 1000);
        this.level = level;
    }

    @Override
    protected void onCreate(@NotNull ItemStack itemStack, @NotNull ItemMeta itemMeta) {
        mute();
        if (0 < durationMillis && durationMillis < 1000) setDurationInfo(itemMeta, (int) durationMillis * 50);
        else hideCooldown();
        itemMeta.setLore(LORE);
    }

    @Override
    protected boolean onUse(@NotNull ItemStack itemStack, @NotNull Player user) {
        UUID uuid = getUniqueId(itemStack);

        if (activeItems.contains(uuid)) return false;

        if (level <= 1) { // Level 1
            Entity closestRunner = plugin.getTagManager().getClosestRunner(user);
            if (closestRunner == null) return false;

            double distance = user.getLocation().distance(closestRunner.getLocation());
            Chat.sendActionBar(user, formatMeters(distance));
        } else { // Level 2
            activeItems.add(uuid);
            long currentTime = System.currentTimeMillis();
            new ItemOngoingUseTaskTimer(plugin, user, itemStack, 10,
                    () -> durationMillis > 0 && System.currentTimeMillis() > currentTime + durationMillis) {
                Entity closestTarget = null;

                @Override
                public void onRecalculation() {
                    closestTarget = plugin.getTagManager().getClosestRunner(user);
                }

                @Override
                public void onTick() {
                    if (durationMillis < 0 && !isItemInHand(user, item)) {
                        Chat.hideActionBar(user);
                    } else if (closestTarget == null) {
                        Chat.sendActionBar(user, ChatColor.YELLOW + "---");
                    } else {
                        double distance = closestTarget.getLocation().distance(user.getLocation());
                        Chat.sendActionBar(user, formatMeters(distance));
                    }
                }

                @Override
                public void onFinish() {
                    Chat.hideActionBar(user);
                    activeItems.remove(uuid);
                }
            }.start();
        }

        SoundEffect.ACTIVATE.play(user);
        return true;
    }

    private static boolean isItemInHand(@NotNull Player player, @NotNull ItemStack itemStack) {
        UUID uuid = getUniqueId(itemStack);
        if (uuid == null) return false;

        PlayerInventory inventory = player.getInventory();

        return uuid.equals(getUniqueId(inventory.getItemInMainHand())) || uuid.equals(getUniqueId(inventory.getItemInOffHand()));
    }

    private static String formatMeters(double blocks) {
        return ChatColor.YELLOW + df.format(blocks) + " m";
    }
}
