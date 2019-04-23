package com.github.xhiroyui.orinbot.modules.command.moderator;

import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.object.util.PermissionSet;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class ModeratorCommands extends Command {
    public ModeratorCommands(String description, int requiredParameters, List<String> parameterDescription, List<String> commandAlias) {
        super(description, requiredParameters, parameterDescription, commandAlias);
    }

    @Override
    protected Mono<Boolean> checkRequiredPermissions(PermissionSet permSet) {
        return Mono.just(true);
    }
}
