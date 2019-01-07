package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.util.BotUtil;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

import java.util.Set;

public class Help extends GeneralCommands implements Command {
    private final int requiredParameters = 1;
    private final String description = "Provides information for commands (including this one)";
    private String[] commandCallers = {"help"};

    @Override
    public Mono<Void> executeCommand(MessageCreateEvent event, String[] args) {
        return Mono.justOrEmpty(event)
                .flatMap(ignored -> processParameters(args, requiredParameters))
                .onErrorResume(error -> BotUtil.COMMAND_ERROR_HANDLER.handle(this, error)
                        .flatMap(errorMessage -> event.getMessage().getChannel()
                                .flatMap(channel -> channel.createMessage(spec -> spec.setContent(errorMessage))))
                        .then(Mono.empty()))
                .flatMap(processedArgs -> runCommand(event, processedArgs))
                .then();
    }

    @Override
    public Mono<Void> runCommand(MessageCreateEvent event, String[] args) {
        return Mono.just(event)
                .map(MessageCreateEvent::getMessage)
                .flatMap(Message::getChannel)
                .zipWith(CommandUtil.commandLookup(args[0]))
                .flatMap(tuple -> tuple.getT1().createMessage(spec -> spec.setEmbed(embedSpec -> tuple.getT2().getCommandInfo(embedSpec))))
                .then();
    }

    @Override
    public EmbedCreateSpec getCommandInfo(EmbedCreateSpec spec) {
        spec.setTitle(this.getClass().getSimpleName());
        spec.setDescription(description);
        return spec;
    }

    @Override
    public String[] getCommandCallers() {
        return this.commandCallers;
    }
}
