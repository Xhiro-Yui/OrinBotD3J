package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.spec.EmbedCreateSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    private static Map<String, Command> commandCallers = new HashMap<>();

    public static void addCommand(Command command) {
        for (String caller : command.getAlias()) {
            commandCallers.put(caller, command);
        }
    }

    public static Mono<? extends Command> commandLookup(String commandCaller) {
        return Mono.justOrEmpty(commandCaller)
                .flatMap(commandsName -> Mono.justOrEmpty(commandCallers.get(commandCaller)));
    }

    public static EmbedCreateSpec generateCommandHelp(EmbedCreateSpec embedSpec, String commandName, String description) {
        return embedSpec.setTitle(commandName)
                .setDescription(description);
    }
}
