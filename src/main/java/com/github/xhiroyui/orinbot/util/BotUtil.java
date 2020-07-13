package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.CommandParameterCountException;
import com.github.xhiroyui.orinbot.modules.CommandParameterValidationException;
import com.github.xhiroyui.orinbot.modules.MissingPermissionsException;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.TextChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
public class BotUtil {

    public static final Long LOG_CHANNEL_ID = 534642277394677770L;
    public static final String[] EMPTY_ARRAY = new String[0];

    public static final ErrorHandler COMMAND_ERROR_HANDLER = (command, error, event) -> {
        if (error instanceof CommandParameterValidationException
                || error instanceof CommandParameterCountException
                || error instanceof MissingPermissionsException ) {
            return Mono.just(error.getMessage());
        }

        log.warn("Command " + command.getClass().getSimpleName() + " faced an unhandled error : " + error.getClass().getSimpleName());

        return event.getClient()
                .getChannelById(Snowflake.of(LOG_CHANNEL_ID))
                .cast(TextChannel.class)
                .flatMap(chn -> chn.createMessage(spec -> spec.setContent(String.format("Command %s hit an unhandled error with the input : `%s`.",
                        command.getClass().getSimpleName(),
                        event.getMessage().getContent()))))
                .map(disposed -> "Unhandled error. Bot author has been notified and will fix this soonᵀᴹ");
    };

    @SneakyThrows
    public static LinkedHashMap<String, String> generateCommandDescriptionMap(List<String> key, List<String> value) {
        if (key.size() != value.size()) {
            throw new InstantiationException("Key and value list must be of same size.");
        }
        LinkedHashMap<String, String> lhm = new LinkedHashMap<>();
        for (int i = 0; i < key.size(); i++) {
            lhm.put(key.get(i), value.get(i));
        }
        return lhm;
    }

}
