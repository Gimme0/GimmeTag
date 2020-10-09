package me.gimme.gimmetag.tag;

import me.gimme.gimmetag.config.Config;
import me.gimme.gimmetag.item.CustomItem;
import me.gimme.gimmetag.item.ItemManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class InventorySupplier {

    private ItemManager itemManager;

    public InventorySupplier(@NotNull ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    /**
     * Sets the inventory of the specified player to the starting state of the specified role.
     *
     * @param player the player to set the inventory for
     * @param role   the role to get the starting inventory state of
     */
    void setInventory(@NotNull Player player, @NotNull Role role) {
        player.getInventory().clear();
        if (Role.HUNTER.equals(role)) setHunterInventory(player.getInventory());
        if (Role.RUNNER.equals(role)) setRunnerInventory(player.getInventory());
    }

    private void setHunterInventory(@NotNull PlayerInventory inventory) {
        addItems(inventory, Config.HUNTER_ITEMS.getValue());

        Color c = Color.fromRGB(Config.HUNTER_LEATHER_COLOR.getValue());
        inventory.setHelmet(getColoredLeatherArmor(ArmorSlot.HEAD, c));
        inventory.setChestplate(getColoredLeatherArmor(ArmorSlot.CHEST, c));
        inventory.setLeggings(getColoredLeatherArmor(ArmorSlot.LEGS, c));
        inventory.setBoots(getColoredLeatherArmor(ArmorSlot.FEET, c));
    }

    private void setRunnerInventory(@NotNull PlayerInventory inventory) {
        addItems(inventory, Config.RUNNER_ITEMS.getValue());
    }


    private static ItemStack getColoredLeatherArmor(@NotNull ArmorSlot armorSlot, @NotNull Color color) {
        Material material;
        switch (armorSlot) {
            case HEAD:
                material = Material.LEATHER_HELMET;
                break;
            case CHEST:
                material = Material.LEATHER_CHESTPLATE;
                break;
            case LEGS:
                material = Material.LEATHER_LEGGINGS;
                break;
            case FEET:
                material = Material.LEATHER_BOOTS;
                break;
            default:
                throw new IllegalStateException("There should only be four armor slots");
        }

        ItemStack armor = new ItemStack(material);
        LeatherArmorMeta meta = Objects.requireNonNull((LeatherArmorMeta) armor.getItemMeta());

        meta.setColor(color);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        armor.setItemMeta(meta);
        return armor;
    }

    private void addItems(@NotNull PlayerInventory inventory, Map<String, Integer> items) {
        items.forEach((itemId, amount) -> {

            // Check if valid custom item
            ItemStack item = itemManager.createItemStack(itemId, amount);
            if (Config.SOULBOUND_ITEMS.getValue() && item != null) CustomItem.soulbind(item, inventory.getHolder());

            // Check if valid normal item
            if (item == null) {
                Material material = Material.matchMaterial(itemId);
                if (material != null) item = new ItemStack(material, amount);
            }

            // Add to inventory if valid item
            if (item != null) inventory.addItem(item);
        });
    }


    private enum ArmorSlot {
        HEAD,
        CHEST,
        LEGS,
        FEET
    }
}
