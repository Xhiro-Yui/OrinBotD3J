package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.modules.command.administrator.Pong;
import com.github.xhiroyui.orinbot.modules.command.general.Help;
import com.github.xhiroyui.orinbot.modules.command.general.Ping;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.DiscordClient;

class DiscordBotSetup {

    void setupBot(DiscordClient client) {
        setupCommands(client);
    }

    private void setupCommands(DiscordClient client) {
        CommandUtil.addCommand(new Ping());
        CommandUtil.addCommand(new Pong());
        CommandUtil.addCommand(new Help());

        CommandHandler commandHandler = new CommandHandler(client);
        commandHandler.handleMCEvent().subscribe();
    }
}
