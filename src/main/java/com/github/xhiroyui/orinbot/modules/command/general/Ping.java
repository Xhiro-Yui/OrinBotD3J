package com.github.xhiroyui.orinbot.modules.command.general;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
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
    public Mono<Void> runCommand(MessageCreateEvent event, String[] args) {
        return Mono.just(event)
                .map(MessageCreateEvent::getMessage)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(spec -> spec.setContent("Pong")))
                .then();
    }
}
