package eu.dudko.create_whatt.mixin.compat.xaeros;

import eu.dudko.create_whatt.compat.xaeros.WorldMapExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.WorldMap;

@Mixin(WorldMap.class)
public abstract class WorldMapMixin {

    @Inject(method = "loadClient", at = @At("RETURN"))
    private void create_whatt$loadWorldMapExtension(CallbackInfo ci) {
        WorldMapExtension.init();
    }

}
