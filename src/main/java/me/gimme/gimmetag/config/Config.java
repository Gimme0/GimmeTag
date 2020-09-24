package me.gimme.gimmetag.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;
import java.util.Set;

public abstract class Config {

    public static final AbstractConfig<Boolean> DISABLE_HUNGER = new ValueConfig<>("disable-hunger", Boolean.class);
    public static final AbstractConfig<Boolean> ENABLE_PVP = new ValueConfig<>("enable-pvp", Boolean.class);


    public static final AbstractConfig<Integer> TAG_SLEEP_TIME = new ValueConfig<>("tag-sleep-time", Integer.class);
    public static final AbstractConfig<Integer> TAG_DEATH_DISTANCE = new ValueConfig<>("tag-death-distance", Integer.class);


    private static final AbstractConfig<ConfigurationSection> SCORING = new ValueConfig<>("scoring", ConfigurationSection.class);
    public static final AbstractConfig<Integer> SCORING_PERIOD = new ValueConfig<>(SCORING, "period", Integer.class);
    public static final AbstractConfig<Integer> SCORING_POINTS_PER_TICK = new ValueConfig<>(SCORING, "points-per-tick", Integer.class);
    public static final AbstractConfig<Integer> SCORING_POINTS_PER_LEVEL = new ValueConfig<>(SCORING, "points-per-level", Integer.class);
    public static final AbstractConfig<Integer> SCORING_POINTS_ON_TAG = new ValueConfig<>(SCORING, "points-on-tag", Integer.class);
    public static final AbstractConfig<Integer> SCORING_POINTS_ON_TAGGED = new ValueConfig<>(SCORING, "points-on-tagged", Integer.class);
    public static final AbstractConfig<Integer> SCORING_DISTANCE_FROM_HUNTER_TO_GET_POINTS = new ValueConfig<>(SCORING, "distance-from-hunter-to-get-points", Integer.class);


    private static final AbstractConfig<ConfigurationSection> HUNTER = new ValueConfig<>("hunter", ConfigurationSection.class);
    public static final AbstractConfig<Map<String, Integer>> HUNTER_ITEMS = new MapConfig<>(HUNTER, "items");
    public static final AbstractConfig<Integer> HUNTER_LEATHER_COLOR = new ValueConfig<>(HUNTER, "leather-color", Integer.class);

    private static final AbstractConfig<ConfigurationSection> RUNNER = new ValueConfig<>("runner", ConfigurationSection.class);
    public static final AbstractConfig<Map<String, Integer>> RUNNER_ITEMS = new MapConfig<>(RUNNER, "items");


    private static final AbstractConfig<ConfigurationSection> CUSTOM_ITEM = new ValueConfig<>("custom-item", ConfigurationSection.class);

    private static final AbstractConfig<ConfigurationSection> SWAPPER_BALL = new ValueConfig<>(CUSTOM_ITEM, "swapper-ball", ConfigurationSection.class);
    public static final AbstractConfig<Number> SWAPPER_BALL_COOLDOWN = new ValueConfig<>(SWAPPER_BALL, "cooldown", Number.class);
    public static final AbstractConfig<Boolean> SWAPPER_ALLOW_HUNTER_SWAP = new ValueConfig<>(SWAPPER_BALL, "allow-hunter-swap", Boolean.class);

    private static final AbstractConfig<ConfigurationSection> INVIS_POTION = new ValueConfig<>(CUSTOM_ITEM, "invis-potion", ConfigurationSection.class);
    public static final AbstractConfig<Number> INVIS_POTION_DURATION = new ValueConfig<>(INVIS_POTION, "duration", Number.class);

    private static final AbstractConfig<ConfigurationSection> SPEED_BOOST = new ValueConfig<>(CUSTOM_ITEM, "speed-boost", ConfigurationSection.class);
    public static final AbstractConfig<Number> SPEED_BOOST_DURATION = new ValueConfig<>(SPEED_BOOST, "duration", Number.class);
    public static final AbstractConfig<Integer> SPEED_BOOST_LEVEL = new ValueConfig<>(SPEED_BOOST, "level", Integer.class);

    public static final AbstractConfig<Set<String>> ENABLE_KNOCKBACK = new SetConfig<>("enable-knockback");
}
