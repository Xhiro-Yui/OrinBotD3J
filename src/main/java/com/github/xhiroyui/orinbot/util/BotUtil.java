package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.CommandParameterValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class BotUtil {

    private static final Logger logger = LoggerFactory.getLogger(BotUtil.class);

    public static final ErrorHandler COMMAND_ERROR_HANDLER = (object, error) -> {
        if (error instanceof CommandParameterValidationException) {
            logger.info("Command " + object.getClass().getSimpleName() + " threw a " + error.getClass().getSimpleName());
        }
        return Mono.empty();
    };
}
