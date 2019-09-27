package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.entity.GuildPrefix;
import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.modules.command.administrator.Pong;
import com.github.xhiroyui.orinbot.modules.command.general.Help;
import com.github.xhiroyui.orinbot.modules.command.general.Ping;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import com.github.xhiroyui.orinbot.util.dao.R2DBCConfig;
import discord4j.core.DiscordClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.DatabaseClient;

/**
 * Utility class to setup and initialize the Discord Bot.
 */
@Slf4j
class DiscordBotSetup {

    void setupBot(DiscordClient client) {
        setupCommands(client);
        testStuffBeforeLogin();
    }

    /**
     * Setup commands to be included into the bot by attaching them to the client before logging in.
     * @param client The Discord Bot client
     */
    private void setupCommands(DiscordClient client) {
        CommandUtil.addCommand(new Ping());
        CommandUtil.addCommand(new Pong());
        CommandUtil.addCommand(new Help());

        CommandHandler commandHandler = new CommandHandler(client);
        commandHandler.handleMCEvent().subscribe();
    }

    /**
     * Basically a place to test stuff that doesn't require the bot to login first.
     */
    private void testStuffBeforeLogin() {
        log.info("Beginning of test stuff");
        R2DBCConfig du = new R2DBCConfig();
        DatabaseClient client = DatabaseClient.create(du.connectionFactory());

        client.insert()
                .into(GuildPrefix.class)
                .using(new GuildPrefix(10293812093L, "bla"))
                .then();

        client.select()
                .from(GuildPrefix.class)
                .fetch()
                .first()
                .doOnNext(it -> log.info(it.toString()));
        log.info("End of test stuff");
    }
}
