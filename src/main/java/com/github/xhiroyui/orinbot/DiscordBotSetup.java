package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.GatewayDiscordClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Component
class DiscordBotSetup {

    @Autowired CommandUtil commandUtil;
    @Autowired CommandHandler commandHandler;
    @Autowired Collection<Command> commandList;

    void setupBot(GatewayDiscordClient gateway) {

        setupCommands(gateway);
    }

    private void setupCommands(GatewayDiscordClient gateway) {
        commandUtil.initializeGuildPrefixes();
        for (Command command : commandList) {
            commandUtil.addCommand(command);
        }
        commandHandler.handleMCEvent().subscribe();
    }
}
