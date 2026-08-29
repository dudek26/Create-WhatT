package eu.dudko.create_whatt;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = CreateWhaTT.ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CreateWhaTT.ID, value = Dist.CLIENT)
public class CreateWhaTTClient {

    public CreateWhaTTClient(ModContainer container) {
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

    }
}
