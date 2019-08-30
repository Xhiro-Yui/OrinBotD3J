package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.command.administrator.Pong;
import com.github.xhiroyui.orinbot.modules.command.general.Help;
import com.github.xhiroyui.orinbot.modules.command.general.Ping;
import com.github.xhiroyui.orinbot.util.BotUtil;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import com.github.xhiroyui.orinbot.util.DatabaseUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import discord4j.core.DiscordClient;

import java.util.HashSet;
import java.util.Set;

class DiscordBotSetup {

    void setupBot(DiscordClient client) {
        setupCommands(client);
        setupDatabase(System.getenv("orinBotD3J_dBUrl"),
                   System.getenv("orinBotD3J_username"),
                   System.getenv("orinBotD3J_pwd")
        );
    }

    private void setupCommands(DiscordClient client) {
        CommandUtil.addCommand(new Ping());
        CommandUtil.addCommand(new Pong());
        CommandUtil.addCommand(new Help());

        CommandHandler commandHandler = new CommandHandler(client);
        commandHandler.handleMCEvent().subscribe();
    }

    private void setupDatabase(String dBUrl, String username, String password) {
        DatabaseUtil.initializeDatabase(dBUrl, username, password);
    }
}
