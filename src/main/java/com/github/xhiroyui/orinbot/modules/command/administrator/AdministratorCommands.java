package com.github.xhiroyui.orinbot.modules.command.administrator;

import com.github.xhiroyui.orinbot.modules.CommandModule;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Permission;
import reactor.core.publisher.Mono;

public abstract class AdministratorCommands extends CommandModule {
    public Mono<Boolean> validatePermissions(Member author) {
        return author.getBasePermissions().map(permSet -> permSet.contains(Permission.ADMINISTRATOR)).filter(Boolean.TRUE::equals);
    }
}
