package com.github.xhiroyui.orinbot.modules.command.general;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.List;

public class SetPrefix extends GeneralCommands {
    public SetPrefix() {
        super("Updates the prefix for this guild.",
                1,
                List.of(),
                List.of("setprefix"));
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
