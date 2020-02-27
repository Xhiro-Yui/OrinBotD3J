package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.modules.command.administrator.Pong;
import com.github.xhiroyui.orinbot.modules.command.general.Help;
import com.github.xhiroyui.orinbot.modules.command.general.Ping;
import com.github.xhiroyui.orinbot.modules.command.general.SetPrefix;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.GatewayDiscordClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
class DiscordBotSetup {

    @Autowired Ping ping;
    @Autowired Pong pong;
    @Autowired Help help;
    @Autowired SetPrefix setPrefix;

    void setupBot(GatewayDiscordClient gateway) {
        setupCommands(gateway);
    }

    private void setupCommands(GatewayDiscordClient gateway) {
        CommandUtil.addCommand(ping);
        CommandUtil.addCommand(pong);
        CommandUtil.addCommand(help);
        CommandUtil.addCommand(setPrefix);

        CommandHandler commandHandler = new CommandHandler(gateway);
        commandHandler.handleMCEvent().subscribe();
    }
}
