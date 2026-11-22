package eu.dudko.create_whatt.foundation;

public class TrainIndexColors {

    public static final int[] COLORS = new int[]{
            0xFF696A6A,
            0xFF8E6348,
            0xFF67BF31,
            0xFF3D6EBD,
            0xFF303036,
            0xFFB63A3A,
            0xFFB63A3A,
            0xFFA558C0,
            0xFF4A555A,
            0xFFEC9E37,
            0xFF21AAAA,
            0xFFDF68BD,
            0xFF8A8A83,
            0xFFE0B73E,
            0xFF44C0D0,
            0xFFF9B3BF
    };

    public static final int[] WAYPOINT_COLORS = new int[]{
            7, 20, 10, 9, 0, 12, 2, 13, 8, 6, 11, 16, 15, 14, 17, 19
    };

    public static int toColor(int mapColorIndex) {
        return COLORS[mapColorIndex];
    }

    public static int toWaypointColorIndex(int mapColorIndex) {
        return WAYPOINT_COLORS[mapColorIndex];
    }

}
