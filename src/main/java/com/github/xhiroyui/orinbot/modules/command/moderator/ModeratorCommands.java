package com.github.xhiroyui.orinbot.modules.command.moderator;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

public abstract class ModeratorCommands {
    public Mono<Boolean> validatePermissions(Channel channel) {
        return Mono.just(true);
    }
}
