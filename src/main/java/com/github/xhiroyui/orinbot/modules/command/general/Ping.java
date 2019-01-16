package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

import java.util.List;

public class Ping extends GeneralCommands {
    public Ping() {
        super("Pongs you.",
                0,
                List.of(),
                List.of("ping"));
    }

    @Override
    public Mono<Void> executeCommand(MessageCreateEvent event, String args) {
        return Mono.justOrEmpty(event)
                .flatMap(ignored -> processParameters(args))
                .flatMap(processedArgs -> runCommand(event, processedArgs))
                .onErrorResume(error -> BotUtil.COMMAND_ERROR_HANDLER.handle(this, error, event)
                        .flatMap(errorMessage -> event.getMessage().getChannel()
                                .flatMap(channel -> channel.createMessage(spec -> spec.setContent(errorMessage))))
                        .then(Mono.empty()))
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
}
