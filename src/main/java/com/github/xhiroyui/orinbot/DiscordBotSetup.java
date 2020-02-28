package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.modules.command.administrator.Pong;
import com.github.xhiroyui.orinbot.modules.command.general.Help;
import com.github.xhiroyui.orinbot.modules.command.general.Ping;
import com.github.xhiroyui.orinbot.modules.command.general.SetPrefix;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.GatewayDiscordClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        for (Command command : commandList) {
            commandUtil.addCommand(command);
        }

        try {
            commandUtil.initializeGuildPrefixes();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        commandHandler.handleMCEvent().subscribe();
    }
}
