package eu.dudko.create_whatt.command.client;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.simibubi.create.CreateClient;
import eu.dudko.create_whatt.compat.xaeros.train_tracker.TrainMapElement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TrainsClientCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> command() {
        return Commands.literal("trains").executes(ctx -> {
            MutableComponent message = Component.literal("Trains:");
            CreateClient.RAILWAYS.trains.forEach((uuid, train) -> {
                TrainMapElement wrapped = new TrainMapElement(train);
                message.append(Component
                        .literal("\n - ")
                        .append(train.name)
                        .append(" (" + uuid + ") at ")
                        .append(wrapped.getPosition(false).toString()));

            });

            ctx.getSource().sendSuccess(() -> message, true);
            return 1;
        });
    }

}
