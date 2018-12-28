package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.CommandParameterValidationException;
import com.github.xhiroyui.orinbot.modules.MissingPermissionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class BotUtil {

    private static final Logger logger = LoggerFactory.getLogger(BotUtil.class);

    public static final ErrorHandler COMMAND_ERROR_HANDLER = (object, error) -> {
        String errorMessage;
        if (error instanceof CommandParameterValidationException) {
            errorMessage = "Command " + object.getClass().getSimpleName() + " threw a " + error.getClass().getSimpleName();
            logger.info(errorMessage);
            return Mono.just(errorMessage);
        }
        if (error instanceof MissingPermissionsException) {
            errorMessage = "Command " + object.getClass().getSimpleName() + " threw a " + error.getClass().getSimpleName();
            logger.info(errorMessage);
            return Mono.just(errorMessage);
        }

        return Mono.just(errorMessage = "Default error message");
    };
}
