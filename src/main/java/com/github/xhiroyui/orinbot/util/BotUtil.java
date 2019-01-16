package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.CommandParameterCountException;
import com.github.xhiroyui.orinbot.modules.CommandParameterValidationException;
import com.github.xhiroyui.orinbot.modules.MissingPermissionsException;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class BotUtil {

    private static final Logger logger = LoggerFactory.getLogger(BotUtil.class);

    private static final long logChannelID = 534642277394677770L;

    public static final ErrorHandler COMMAND_ERROR_HANDLER = (object, error, event) -> {
        if (error instanceof CommandParameterValidationException) {
            return Mono.just("Incorrect parameters in **" + object.getClass().getSimpleName() + "** command. Use `help` command for more info regarding the required command parameters.");
        }
        if (error instanceof CommandParameterCountException) {
            return Mono.just("Insufficient parameters in **" + object.getClass().getSimpleName() + "** command. Use `help` command for more info regarding the required amount of command parameters.");
        }
        if (error instanceof MissingPermissionsException) {
            return Mono.just("Insufficient permissions");
        }

        logger.warn("Command " + object.getClass().getSimpleName() + " faced an unhandled error : " + error.getClass().getSimpleName());

        return event.getClient()
                .getChannelById(Snowflake.of(logChannelID))
                .cast(TextChannel.class)
                .flatMap(chn -> chn.createMessage(spec -> spec.setContent(String.format("Command %s hit an unhandled error with the input : `%s`.", object.getClass().getSimpleName(), event.getMessage().getContent().get()))))
                .map(disposed -> "Unhandled error. Bot author has been notified and will fix this soonᵀᴹ");
    };

    public static final String[] EMPTY_ARRAY = new String[0];

}
