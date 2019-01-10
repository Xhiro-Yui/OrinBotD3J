package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.CommandParameterCountException;
import com.github.xhiroyui.orinbot.modules.CommandParameterValidationException;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Permission;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class GeneralCommands extends Command {
    public GeneralCommands(String description, int requiredParameters, List<String> parameterDescription, List<String> commandAlias) {
        super(description, requiredParameters, parameterDescription, commandAlias);
    }

    public Mono<Boolean> validatePermissions(Member author) {
        return Mono.just(true);
    }

}
