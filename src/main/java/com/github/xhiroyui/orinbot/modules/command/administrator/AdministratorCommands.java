package com.github.xhiroyui.orinbot.modules.command.administrator;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.CommandModule;
import com.github.xhiroyui.orinbot.modules.MissingPermissionsException;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import reactor.core.publisher.Mono;

public abstract class AdministratorCommands extends CommandModule {
    public Mono<PermissionSet> validatePermissions(Member author) {
        return Mono.just(author)
                .flatMap(Member::getBasePermissions)
                .filterWhen(this::checkRequiredPermissions)
                .switchIfEmpty(Mono.error(new MissingPermissionsException(author)))
                ;
    }

    public Mono<Boolean> checkRequiredPermissions(PermissionSet permSet) {
        if (permSet.contains(Permission.ADMINISTRATOR))
            return Mono.just(true);
        return Mono.empty();
    }
}
