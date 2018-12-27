package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.command.administrator.Pong;
import com.github.xhiroyui.orinbot.modules.command.general.Ping;
import discord4j.core.DiscordClient;

import java.util.HashSet;
import java.util.Set;

public class DiscordBotSetup {

    public void setupBot(DiscordClient client) {
        setupCommands(client);
    }

    private void setupCommands(DiscordClient client) {
        Set<Command> commands = new HashSet<>(4);
        commands.add(new Ping());
        commands.add(new Pong());


        CommandHandler commandHandler = new CommandHandler(client, commands);
        commandHandler.handleMCEvent().subscribe();
    }
}
