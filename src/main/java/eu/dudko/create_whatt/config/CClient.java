package eu.dudko.create_whatt.config;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.createmod.catnip.config.ConfigBase;
import net.createmod.catnip.config.ui.ConfigAnnotations;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CClient extends ConfigBase {

    public final ConfigGroup client = group(0, "client", Comments.client);

    public final ConfigBool showOfflineOwners = b(true, "showOfflineOwners", Comments.showOfflineOwners);
    public final ConfigBool showOfflineOwnerIcon = b(true, "showOfflineOwnerIcon", Comments.showOfflineOwnerIcon);
    public final ConfigInt offlineOwnerNameColor = i(0xE0A3A3, "offlineOwnerNameColor", Comments.offlineOwnerNameColor);

    public final ConfigBool showTrainUuid = b(false, "showTrainUuid", Comments.showTrainUuid);

    @Override
    public String getName() {
        return "client";
    }

    private static class Comments {
        static String client = "Client-only settings";
        static String showOfflineOwners = "Show train owners on map even if they're offline.";
        static String showOfflineOwnerIcon = "Show \uEF23 icon next to offline player train owner name, if enabled.";
        static String[] offlineOwnerNameColor = new String[]{
                "The custom text color to use for offline train owner name, if enabled.",
                "[in Hex: #RrGgBb]",
                ConfigAnnotations.IntDisplay.HEX.asComment()
        };
        static String showTrainUuid = "Show train UUID on map.";
    }
}
