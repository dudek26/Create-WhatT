package eu.dudko.create_whatt;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CreateWhaTT.ID)
public class CreateWhaTT {
    public static final String ID = "create_whatt";
    public static final String NAME = "Create: WhaTT";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateWhaTT(IEventBus eventBus, ModContainer container) {
        NeoForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation id(String key) {
        return ResourceLocation.fromNamespaceAndPath(CreateWhaTT.ID, key);
    }

}
