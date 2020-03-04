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

    Flux<Integer> aFlux = Flux.just(10, 20, 30, 40, 50);
    Flux<Integer> bFlux = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    Flux<Integer> cFlux = Flux.just(100, 200, 300);

    public Flux<GuildPrefix> initializeGuildPrefixes() {
        return commandUtil.initializeGuildPrefixes();
    }

    public Mono<GatewayDiscordClient> postLoginSetup(GatewayDiscordClient gateway) {
        log.info("Starting postLogin Setup.");
        setupCommands(gateway);
        return Mono.just(gateway);
    }

    private Mono<GatewayDiscordClient> setupCommands(GatewayDiscordClient gateway) {
        for (Command command : commandList) {
            commandUtil.addCommand(command);
        }
        commandHandler.handleMCEvent(gateway).subscribe();
        return Mono.just(gateway);
    }
}
