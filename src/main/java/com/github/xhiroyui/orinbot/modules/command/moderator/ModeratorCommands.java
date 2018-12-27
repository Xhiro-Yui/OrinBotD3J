package com.github.xhiroyui.orinbot.modules.command.moderator;

import com.github.xhiroyui.orinbot.modules.CommandModule;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

public abstract class ModeratorCommands extends CommandModule {
    public Mono<Boolean> validatePermissions(Channel channel) {
        return Mono.just(true);
    }
}
