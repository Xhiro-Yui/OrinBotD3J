package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.datastore.entity.GuildPrefix;
import com.github.xhiroyui.orinbot.modules.CommandHandler;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
class DiscordBotSetup {

    @Autowired CommandUtil commandUtil;
    @Autowired CommandHandler commandHandler;
    @Value("${bot.whitelist:#{null}}") String[] userIdWhitelist;

    public Flux<GuildPrefix> initializeGuildPrefixes() {
        return commandUtil.initializeGuildPrefixes();
    }

    public Flux<Snowflake> initializeWhitelist() {
        return commandUtil.initializeUserWhitelist(userIdWhitelist);
    }

    public Mono<GatewayDiscordClient> setupCommands(GatewayDiscordClient gateway) {
        commandHandler.handleMCEvent(gateway).subscribe();
        return Mono.just(gateway);
    }

}
