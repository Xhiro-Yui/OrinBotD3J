package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.CommandParameterCountException;
import com.github.xhiroyui.orinbot.modules.CommandParameterValidationException;
import com.github.xhiroyui.orinbot.modules.MissingPermissionsException;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.util.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

//import discord4j.core.object.entity.TextChannel;

public class BotUtil {

    private static final Logger log = LoggerFactory.getLogger(BotUtil.class);

    public static final ErrorHandler COMMAND_ERROR_HANDLER = (command, error, event) -> {
        if (error instanceof CommandParameterValidationException) {
            return Mono.just("Incorrect parameters in **" + command.getClass().getSimpleName() + "** command. Use `help` command for more info regarding the required command parameters.");
        }
        if (error instanceof CommandParameterCountException) {
            return Mono.just("Insufficient parameters in **" + command.getClass().getSimpleName() + "** command. Use `help` command for more info regarding the required amount of command parameters.");
        }
        if (error instanceof MissingPermissionsException) {
            return Mono.just("Insufficient permissions");
        }

        log.warn("Command " + command.getClass().getSimpleName() + " faced an unhandled error : " + error.getClass().getSimpleName());

        return event.getClient()
                .getChannelById(Snowflake.of(BotConstant.LOG_CHANNEL_ID))
                .cast(TextChannel.class)
                .flatMap(chn -> chn.createMessage(spec -> spec.setContent(String.format("Command %s hit an unhandled error with the input : `%s`.", command.getClass().getSimpleName(), event.getMessage().getContent().orElse(null)))))
                .map(disposed -> "Unhandled error. Bot author has been notified and will fix this soonᵀᴹ");
    };

    public static final String[] EMPTY_ARRAY = new String[0];

}
