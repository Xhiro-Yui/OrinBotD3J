package com.github.xhiroyui.orinbot.modules.command.administrator;

import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class AdministratorCommands extends Command{
    public AdministratorCommands(String description, int requiredParameters, List<String> parameterDescription, List<String> commandAlias) {
        super(description, requiredParameters, parameterDescription, commandAlias);
    }

    @Override
    protected Mono<Boolean> checkRequiredPermissions(PermissionSet permSet) {
        if (permSet.contains(Permission.ADMINISTRATOR))
            return Mono.just(true);
        return Mono.empty();
    }
}
