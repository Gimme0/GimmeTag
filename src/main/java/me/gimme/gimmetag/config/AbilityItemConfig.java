package me.gimme.gimmetag.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class AbilityItemConfig extends ValueConfig<ConfigurationSection> {

    private static final String COOLDOWN_PATH = "cooldown";
    private static final String CONSUMABLE_PATH = "consumable";
    private static final String DURATION_PATH = "duration";
    private static final String LEVEL_PATH = "level";

    public AbilityItemConfig(@NotNull ConfigurationSection parent, @NotNull String path) {
        this(FileConfiguration.createPath(parent, path));
    }

    AbilityItemConfig(@NotNull AbstractConfig<ConfigurationSection> parent, @NotNull String path) {
        super(parent, path, ConfigurationSection.class);
    }

    AbilityItemConfig(@NotNull String path) {
        super(path, ConfigurationSection.class);
    }

    public double getCooldown() {
        return getValue().getDouble(COOLDOWN_PATH);
    }

    public boolean isConsumable() {
        return getValue().getBoolean(CONSUMABLE_PATH);
    }

    public double getDuration() {
        return getValue().getDouble(DURATION_PATH);
    }

    public int getLevel() {
        return getValue().getInt(LEVEL_PATH);
    }

    public boolean hasDuration() {
        return getValue().contains(DURATION_PATH);
    }

    public boolean hasLevel() {
        return getValue().contains(LEVEL_PATH);
    }
}
