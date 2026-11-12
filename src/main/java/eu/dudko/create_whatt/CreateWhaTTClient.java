package eu.dudko.create_whatt;

import com.simibubi.create.content.trains.entity.Train;
import eu.dudko.create_whatt.config.WhattConfigs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Supplier;

@Mod(value = CreateWhaTT.ID, dist = Dist.CLIENT)
public class CreateWhaTTClient {

    public static Train HOVERED_TRAIN = null;

    private final ModContainer container;

    public CreateWhaTTClient(ModContainer container, IEventBus eventBus) {
        this.container = container;
        eventBus.addListener(this::onLoadComplete);

    }

    private void onLoadComplete(FMLLoadCompleteEvent event) {
        Supplier<IConfigScreenFactory>
                configScreen =
                () -> (mc, previousScreen) -> WhattConfigs.createConfigScreen(previousScreen);
        container.registerExtensionPoint(IConfigScreenFactory.class, configScreen);
    }
}
