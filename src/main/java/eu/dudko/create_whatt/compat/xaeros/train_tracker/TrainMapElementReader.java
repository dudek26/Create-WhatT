package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import eu.dudko.create_whatt.compat.xaeros.SupportModsExtension;
import eu.dudko.create_whatt.config.CClient;
import eu.dudko.create_whatt.config.WhattConfigs;
import eu.dudko.create_whatt.foundation.TrainIndexColors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Style;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.WorldMap;
import xaero.map.WorldMapSession;
import xaero.map.element.MapElementReader;
import xaero.map.gui.GuiMap;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.dropdown.rightclick.RightClickOption;
import xaero.map.gui.util.GuiUtils;
import xaero.map.mods.SupportMods;
import xaero.map.teleport.MapTeleporter;
import xaero.map.util.DistanceUtils;
import xaero.map.world.MapDimension;
import xaero.map.world.MapWorld;

import java.util.ArrayList;

public class TrainMapElementReader extends MapElementReader<TrainMapElement, TrainMapElementRenderContext, TrainMapElementRenderer> {

    @Override
    public boolean isHidden(TrainMapElement element, TrainMapElementRenderContext context) {
        return true;
    }

    public boolean isSameDimension(TrainMapElement element, MapWorld mapWorld) {
        MapDimension currentDimension = mapWorld.getCurrentDimension();
        MapDimension trainDimension = mapWorld.getDimension(element.getDimensions().getFirst());
        return currentDimension == trainDimension;
    }

    @Override
    public ArrayList<RightClickOption> getRightClickOptions(TrainMapElement element, IRightClickableElement target) {

        CClient config = WhattConfigs.client();

        ArrayList<RightClickOption> options = new ArrayList<>();
        //noinspection ConstantValue
        options.add(new RightClickOption(this.getMenuName(element), options.size(), target) {
            @Override
            public void onAction(Screen screen) {
            }
        });

        int x = (int) Math.floor(element.getX(false));
        int y = (int) Math.floor(element.getY(false));
        int z = (int) Math.floor(element.getZ(false));

        options.add(new RightClickOption("", Style.EMPTY.withColor(ChatFormatting.GRAY), options.size(), target) {
            @Override
            public String getName() {
                return String.format("X: %d, Y: %s, Z: %d", x, y, z);
            }

            @Override
            public void onAction(Screen screen) {
            }
        });
        options.add((new RightClickOption("gui.xaero_whatt_right_click_train_teleport", options.size(), target) {
            @Override
            public void onAction(Screen screen) {
                WorldMapSession session = WorldMapSession.getCurrentSession();
                new MapTeleporter().teleport(screen,
                        session.getMapProcessor().getMapWorld(),
                        x,
                        y + config.trainMapTeleportYOffset.get(),
                        z,
                        element.getDimensions().getFirst());
            }
        }).setNameFormatArgs(GuiUtils.getBoundKeyComponent("T")));

        if (SupportMods.minimap() && target instanceof GuiMap guiMap) {
            options.add(new RightClickOption("gui.xaero_whatt_right_click_train_share_location",
                    options.size(),
                    target) {
                @Override
                public void onAction(Screen screen) {
                    SupportModsExtension.xaeroMinimap.shareTrainLocation(guiMap, element);
                }
            });
        }

        Minecraft mc = Minecraft.getInstance();
        options.add(new RightClickOption("gui.xaero_whatt_copy_train_name", options.size(), target) {
            @Override
            public void onAction(Screen screen) {
                mc.keyboardHandler.setClipboard(element.getTrainNameString());
            }
        });

        options.add(new RightClickOption("gui.xaero_whatt_copy_train_uuid", options.size(), target) {
            @Override
            public void onAction(Screen screen) {
                mc.keyboardHandler.setClipboard(element.getTrainId().toString());
            }
        });

        ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
        DistanceUtils.addDistanceRightClickOption(element.getX(false),
                element.getY(false),
                element.getZ(false),
                false,
                target,
                mc.getCameraEntity(),
                mc.getTimer().getGameTimeDeltaPartialTick(true),
                configManager,
                options);

        return options;
    }

    @Override
    public double getRenderX(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return element.getX(true);
    }

    @Override
    public double getRenderY(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return element.getY(true);
    }

    @Override
    public double getRenderZ(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return element.getZ(true);
    }

    @Override
    public int getInteractionBoxLeft(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxRight(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxTop(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxBottom(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxLeft(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxRight(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxTop(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxBottom(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getLeftSideLength(TrainMapElement element, Minecraft mc) {
        return 9 + mc.font.width(I18n.get(element.getTrainNameString()));
    }

    @Override
    public String getMenuName(TrainMapElement element) {
        return element.getTrainNameString();
    }

    @Override
    public String getFilterName(TrainMapElement element) {
        return this.getMenuName(element);
    }

    @Override
    public int getMenuTextFillLeftPadding(TrainMapElement element) {
        return 0;
    }

    @Override
    public int getRightClickTitleBackgroundColor(TrainMapElement element) {
        return TrainIndexColors.toColor(element.train().mapColorIndex);
    }

    @Override
    public boolean shouldScaleBoxWithOptionalScale() {
        return true;
    }

    @Override
    public boolean hasYCoordinate() {
        return true;
    }
}
