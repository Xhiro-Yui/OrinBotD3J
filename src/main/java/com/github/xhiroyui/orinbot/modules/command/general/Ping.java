package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

public class Ping extends GeneralCommands implements Command{
    private final int requiredParameters = 0;
    private final String description = "Pongs you.";
    private String[] commandCallers = {"ping"};

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
