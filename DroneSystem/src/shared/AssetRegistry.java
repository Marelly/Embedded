package shared;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * AssetRegistry maps symbolic keys to actual asset files.
 *
 * The backend must NEVER use raw URLs.
 * The UI reads this registry and loads the real images.
 *
 */
public final class AssetRegistry {

    // ----- Public symbolic keys (used by backend) -----

    public static final String MAP_DESERT = "map_desert";
    public static final String DRONE = "drone";
    public static final String TARGET = "target";
    public static final String TARGET_REACHED = "target_reached";
    public static final String NO_FLY_ZONE = "no_fly_zone";
    public static final String EXPLOSION = "explosion";

    // ----- Internal storage -----

    private static final Map<String, String> assets;

    static {
        Map<String, String> map = new HashMap<>();

        // -------------------------------
        // Backgrounds
        // -------------------------------
        map.put(MAP_DESERT,"resources/map_desert.jpg");

        // -------------------------------
        // Sprites
        // -------------------------------
        map.put(DRONE,"resources/drone.png");

        map.put(TARGET,"resources/target.png");

        map.put(TARGET_REACHED,"resources/target_reached.png");

        map.put(NO_FLY_ZONE, "resources/NoFlyZone.png");

        map.put(EXPLOSION, "resources/explosion.jpg");

        assets = Collections.unmodifiableMap(map);
    }

    /**
     * Returns the URL or local path associated with a symbolic key.
     */
    public static String get(String key) {
        String value = assets.get(key);
        if (value == null) {
            throw new IllegalArgumentException("No asset registered for key: " + key);
        }
        return value;
    }

    /**
     * Returns true if the registry contains the key.
     */
    public static boolean contains(String key) {
        return assets.containsKey(key);
    }
}