package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.spec.EmbedCreateSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class CommandUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    // Command Callers
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

    // Guild Prefixes
    private static Map<Long, String> guildPrefixes = new HashMap<>();

    public static String getGuildPrefix(long guildId) {
        if (guildPrefixes.containsKey(guildId)) {
            return guildPrefixes.get(guildId);
        }
        return "~";
    }

    public static void initializeGuildPrefixes() {
        //TODO change to grab from DB
        guildPrefixes = new HashMap<>();
        guildPrefixes.put(1234567890L, "Dummy values until DB is set up");
    }

    public static void updateGuildPrefixes() {
        //TODO change to update from DB
    }
}
