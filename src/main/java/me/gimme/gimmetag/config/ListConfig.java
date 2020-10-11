package me.gimme.gimmetag.config;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

class ListConfig<T> extends AbstractConfig<List<T>> {
    ListConfig(@NotNull AbstractConfig<ConfigurationSection> parent, @NotNull String path) {
        super(parent, path, null);
    }

    ListConfig(@NotNull String path) {
        super(path, null);
    }

    @NotNull
    @Override
    public List<T> getValue() {
        return (List<T>) Objects.requireNonNull(getConfig().getList(path));
    }
}
