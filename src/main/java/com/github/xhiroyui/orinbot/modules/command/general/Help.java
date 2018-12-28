package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class Help extends GeneralCommands implements Command {
    private final int requiredParameters = 1;
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
                .flatMap(channel -> channel.createMessage(spec -> spec.setContent("Pong")))
                .then();
    }

    @Override
    public Mono<Void> getCommandInfo(MessageCreateEvent event, String[] args) {
        return Mono.empty();
    }

    @Override
    public String[] getCommandCallers() {
        return this.commandCallers;
    }
}
