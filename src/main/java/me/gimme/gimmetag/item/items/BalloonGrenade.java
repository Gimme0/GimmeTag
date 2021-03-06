package me.gimme.gimmetag.item.items;

import me.gimme.gimmetag.config.type.AbilityItemConfig;
import me.gimme.gimmetag.item.AbilityItem;
import me.gimme.gimmetag.sfx.SoundEffects;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class BalloonGrenade extends AbilityItem {

    private static final String NAME = "Balloon Grenade";
    private static final Material MATERIAL = Material.SPLASH_POTION;
    private static final Color COLOR = Color.fromRGB(137, 208, 229);

    public BalloonGrenade(@NotNull String id, @NotNull AbilityItemConfig config) {
        super(id, NAME, MATERIAL, config);

        setUseSound(SoundEffects.THROW);
    }

    @Override
    protected void onCreate(@NotNull ItemStack itemStack, @NotNull ItemMeta itemMeta) {
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.LEVITATION, getDurationTicks(), getAmplifier());

        PotionMeta potionMeta = (PotionMeta) itemMeta;
        potionMeta.setColor(COLOR);
        potionMeta.addCustomEffect(potionEffect, true);
    }

    @Override
    protected boolean onUse(@NotNull ItemStack itemStack, @NotNull Player user) {
        ThrownPotion thrownPotion = user.launchProjectile(ThrownPotion.class);
        thrownPotion.setShooter(user);
        thrownPotion.setItem(itemStack);

        return true;
    }
}
