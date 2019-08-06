package com.github.xhiroyui.orinbot.modules.command.administrator;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class AdministratorCommands extends Command{
    AdministratorCommands(String description, int requiredParameters, List<String> parameterDescription, List<String> commandAlias) {
        super(description, requiredParameters, parameterDescription, commandAlias);
    }

    @Override
    protected Mono<Void> executeCommand(MessageCreateEvent event, String args) {
        return Mono.justOrEmpty(event)
                .flatMap(mce -> mce.getMessage().getChannel().zipWith(event.getMessage().getAuthorAsMember()).flatMap(this::validatePermissions))
                .onErrorResume(error -> BotUtil.COMMAND_ERROR_HANDLER.handle(this, error, event)
                        .flatMap(errorMessage -> event.getMessage().getChannel()
                                .flatMap(channel -> channel.createMessage(spec -> spec.setContent(errorMessage))))
                        .then(Mono.empty()))
                .flatMap(ignored -> executeCommandInternals(event, args));
    }

    @Override
    protected Mono<Boolean> checkRequiredPermissions(PermissionSet permSet) {
        if (permSet.contains(Permission.ADMINISTRATOR))
            return Mono.just(true);
        return Mono.empty();
    }
}
