package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.datastore.entity.GuildPrefix;
import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.GatewayDiscordClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;

@Slf4j
@Component
class DiscordBotSetup {

    @Autowired CommandUtil commandUtil;
    @Autowired CommandHandler commandHandler;
    @Autowired Collection<Command> commandList;

    public Flux<GuildPrefix> initializeGuildPrefixes() {
        return commandUtil.initializeGuildPrefixes();
    }

    public Mono<GatewayDiscordClient> setupCommands(GatewayDiscordClient gateway) {
        for (Command command : commandList) {
            commandUtil.addCommand(command);
        }
        commandHandler.handleMCEvent(gateway).subscribe();
        return Mono.just(gateway);
    }
}
