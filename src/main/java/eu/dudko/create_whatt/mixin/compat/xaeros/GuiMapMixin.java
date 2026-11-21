package eu.dudko.create_whatt.mixin.compat.xaeros;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import eu.dudko.create_whatt.CreateWhaTT;
import eu.dudko.create_whatt.compat.xaeros.WorldMapExtension;
import eu.dudko.create_whatt.compat.xaeros.train_tracker.TrainMapElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.lib.client.gui.ScreenBase;
import xaero.lib.client.gui.widget.Tooltip;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.element.HoveredMapElementHolder;
import xaero.map.element.MapElementMenuHitbox;
import xaero.map.gui.GuiMap;
import xaero.map.gui.GuiTexturedButton;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.MapMouseButtonPress;
import xaero.map.world.MapDimension;

import static eu.dudko.create_whatt.compat.xaeros.train_tracker.TrainMenuRenderer.*;

@Mixin(value = GuiMap.class)
public abstract class GuiMapMixin extends ScreenBase implements IRightClickableElement {

    protected GuiMapMixin(Screen parent, Screen escape, Component titleIn) {
        super(parent, escape, titleIn);
    }

    @Shadow
    protected abstract void unfocusAll();

    @Shadow
    public abstract <T extends GuiEventListener & Renderable & NarratableEntry> T addButton(T guiEventListener);

    @Shadow
    private double scale;
    @Shadow
    private MapMouseButtonPress leftMouseButton;
    @Shadow
    private HoveredMapElementHolder<?, ?> viewed;
    @Shadow
    private boolean viewedInList;

    @Shadow
    private MapProcessor mapProcessor;
    @Shadow
    private int[] cameraDestination;
    @Shadow
    private static boolean attachedCamera;
    @Shadow
    public boolean shouldReinit;
    @Shadow
    public boolean playersMenu;

    @Shadow
    protected abstract void togglePlayerMenu();

    @Shadow
    public boolean waypointMenu;

    @Shadow
    protected abstract void toggleWaypointMenu();

    @Unique
    private boolean create_whatt$trainsMenu = false;
    @Unique
    private Button create_whatt$trainsButton;
    @Unique
    private boolean create_whatt$overTrainsMenu;

    // START: Trains Menu

    @Inject(method = "init", at = @At("RETURN"))
    public void create_whatt$initExtras(CallbackInfo ci) {
        Tooltip
                trainsTooltip =
                new Tooltip(this.create_whatt$trainsMenu ?
                            "gui.xaero_whatt_box_close_trains" :
                            "gui.xaero_whatt_box_open_trains");
        this.addButton(
                this.create_whatt$trainsButton =
                        new GuiTexturedButton(this.width - TRAINS_BUTTON_X,
                                create_whatt$findTrainsButtonTop(),
                                TRAINS_BUTTON_W,
                                TRAINS_BUTTON_H,
                                TRAINS_ICON_TEXTURE_X,
                                TRAINS_ICON_TEXTURE_Y,
                                TRAINS_ICON_TEXTURE_WIDTH,
                                TRAINS_ICON_TEXTURE_HEIGHT,
                                TRAINS_ICON_TEXTURE,
                                this::create_whatt$onTrainsButton,
                                () -> trainsTooltip));

        WorldMapExtension.trainsMenuRenderer.onMapInit(create_whatt$self(), this.minecraft, this.width, this.height);
    }

    @Inject(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",
                    ordinal = 9,
                    shift = At.Shift.AFTER))
    public void create_whatt$renderTrainsMenu(CallbackInfo ci, @Local(argsOnly = true) GuiGraphics guiGraphics, @Local(
            name = "scaledMouseX") int scaledMouseX, @Local(name = "scaledMouseY") int scaledMouseY, @Local(name = "mc") Minecraft mc) {
        this.create_whatt$overTrainsMenu = false;
        if (this.create_whatt$trainsMenu) {
            HoveredMapElementHolder<?, ?>
                    hovered =
                    WorldMapExtension.trainsMenuRenderer.renderMenu(guiGraphics,
                            (GuiMap) (Object) this,
                            this.scale,
                            this.width,
                            this.height,
                            scaledMouseX,
                            scaledMouseY,
                            this.leftMouseButton.isDown,
                            this.leftMouseButton.clicked,
                            this.viewed,
                            mc);
            if (hovered != null) {
                this.create_whatt$overTrainsMenu = true;
                if (!(hovered.getElement() instanceof MapElementMenuHitbox)) {
                    this.viewed = hovered;
                    this.viewedInList = true;
                }

                if (hovered.getElement() instanceof TrainMapElement element
                        && WorldMapExtension.trainsMenuRenderer.canJumpTo(element, this.mapProcessor.getMapWorld())
                        && this.leftMouseButton.clicked) {
                    TrainMapElement clickedTrain = (TrainMapElement) this.viewed.getElement();
                    ResourceKey<Level> clickedTrainDim = clickedTrain.getDimensions().getFirst();
                    MapDimension clickedTrainMapDim = this.mapProcessor.getMapWorld().getDimension(clickedTrainDim);
                    DimensionType
                            clickedTrainDimType =
                            MapDimension.getDimensionType(clickedTrainMapDim,
                                    clickedTrain.getDimensions().getFirst(),
                                    this.mapProcessor.getWorldDimensionTypeRegistry());
                    double
                            clickedTrainDimDiv =
                            this.mapProcessor
                                    .getMapWorld()
                                    .getCurrentDimension()
                                    .calculateDimDiv(this.mapProcessor.getWorldDimensionTypeRegistry(),
                                            clickedTrainDimType);
                    CreateWhaTT.LOGGER.info("clicked train: {} / {}\ndim: div: {}, current: {}, clickedTrainDim: {}",
                            clickedTrain.getX(false),
                            clickedTrain.getZ(false),
                            clickedTrainDimDiv,
                            this.mapProcessor.getMapWorld().getCurrentDimension().getDimId(),
                            clickedTrainMapDim.getDimId());
                    double jumpX = clickedTrain.getX(false) / clickedTrainDimDiv;
                    double jumpZ = clickedTrain.getZ(false) / clickedTrainDimDiv;
                    this.cameraDestination = new int[]{(int) jumpX, (int) jumpZ};
                    this.leftMouseButton.isDown = false;
                    if (attachedCamera) {
                        attachedCamera = false;
                        this.shouldReinit = true;
                    }
                }
            }
        }
    }

    @Inject(method = "renderPreDropdown", at = @At("RETURN"))
    public void create_whatt$trainsPreDropdown(GuiGraphics guiGraphics, int scaledMouseX, int scaledMouseY, float partialTicks, CallbackInfo ci) {
        if (this.create_whatt$trainsMenu) {
            WorldMapExtension.trainsMenuRenderer.postMapRender(guiGraphics,
                    (GuiMap) (Object) this,
                    scaledMouseX,
                    scaledMouseY,
                    this.width,
                    this.height,
                    partialTicks);
        }
    }

    @ModifyExpressionValue(method = "render",
            at = @At(value = "FIELD",
                    target = "Lxaero/map/gui/GuiMap;playersMenu:Z",
                    ordinal = 0,
                    opcode = Opcodes.GETFIELD))
    private boolean create_whatt$renderingMenus(boolean original) {
        return original || this.create_whatt$trainsMenu;
    }

    @Inject(method = "mouseScrolled", at = @At("HEAD"), cancellable = true)
    private void create_whatt$scrollTrainsMenu(double par1, double par2, double g, double wheel, CallbackInfoReturnable<Boolean> cir) {
        int direction = wheel > 0.0 ? 1 : -1;
        if (this.create_whatt$trainsMenu && this.create_whatt$overTrainsMenu) {
            WorldMapExtension.trainsMenuRenderer.mouseScrolled(direction);
            cir.setReturnValue(super.mouseScrolled(par1, par2, g, wheel));
        }
    }

    @Inject(method = {"toggleWaypointMenu", "togglePlayerMenu"}, at = @At("HEAD"))
    private void create_whatt$toggleMenus(CallbackInfo ci) {
        if (this.create_whatt$trainsMenu) {
            this.create_whatt$toggleTrainsMenu();
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"))
    private void create_whatt$trainsMouseReleased(double par1, double par2, int par3, CallbackInfoReturnable<Boolean> cir) {
        if (this.create_whatt$trainsMenu) {
            WorldMapExtension.trainsMenuRenderer.onMapMouseRelease(par1, par2, par3);
        }
    }

    @Inject(method = "unfocusAll", at = @At("HEAD"))
    private void create_whatt$unfocusAll(CallbackInfo ci) {
        WorldMapExtension.trainsMenuRenderer.unfocusAll();
    }

    @Inject(method = "charTyped", at = @At("HEAD"), cancellable = true)
    private void create_whatt$charTyped(char par1, int par2, CallbackInfoReturnable<Boolean> cir) {
        if (this.create_whatt$trainsMenu && WorldMapExtension.trainsMenuRenderer.charTyped()) {
            super.charTyped(par1, par2);
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "keyPressed", at = @At(value = "RETURN", ordinal = 3), cancellable = true)
    private void create_whatt$keyPressed(int par1, int par2, int par3, CallbackInfoReturnable<Boolean> cir) {
        if (this.create_whatt$trainsMenu && WorldMapExtension.trainsMenuRenderer.keyPressed(create_whatt$self(),
                par1)) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private void create_whatt$toggleTrainsMenu() {
        if (this.playersMenu) {
            this.togglePlayerMenu();
        }

        if (this.waypointMenu) {
            this.toggleWaypointMenu();
        }

        this.create_whatt$trainsMenu = !this.create_whatt$trainsMenu;
        if (!this.create_whatt$trainsMenu) {
            WorldMap.trackedPlayerMenuRenderer.onMenuClosed();
            this.unfocusAll();
        }
    }

    @Unique
    private void create_whatt$onTrainsButton(Button b) {
        this.create_whatt$toggleTrainsMenu();
        this.init(this.minecraft, this.width, this.height);
        this.setFocused(this.create_whatt$trainsButton);
    }

    @Unique
    private int create_whatt$findTrainsButtonTop() {
        int topmostRightButton = this.height;
        for (Renderable renderable : renderables) {
            if (renderable instanceof Button button
                    && button.getX() == this.width - TRAINS_BUTTON_W
                    && button.getY() >= this.height / 2) {
                topmostRightButton = Math.min(topmostRightButton, button.getY());
            }
        }
        return Math.max(0, topmostRightButton - TRAINS_BUTTON_W);
    }

    // END: Trains Menu

    @Unique
    private GuiMap create_whatt$self() {
        return (GuiMap) (Object) this;
    }

}
