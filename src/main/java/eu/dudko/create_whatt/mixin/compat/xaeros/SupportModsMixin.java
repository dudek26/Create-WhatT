package eu.dudko.create_whatt.mixin.compat.xaeros;

import eu.dudko.create_whatt.compat.xaeros.SupportModsExtension;
import eu.dudko.create_whatt.compat.xaeros.SupportXaeroMinimapExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.mods.SupportMods;

@Mixin(SupportMods.class)
public abstract class SupportModsMixin {

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lxaero/map/mods/SupportXaeroMinimap;register()V"))
    private void create_whatt$supportMinimap(CallbackInfo ci) {
        SupportModsExtension.xaeroMinimap = new SupportXaeroMinimapExtension();
    }

}
