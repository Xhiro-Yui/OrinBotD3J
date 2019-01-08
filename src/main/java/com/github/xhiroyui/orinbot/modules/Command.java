package com.github.xhiroyui.orinbot.modules;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

public interface Command {
    Mono<Void> executeCommand(MessageCreateEvent event, String args);

    Mono<Void> runCommand(MessageCreateEvent event, String[] args);

    EmbedCreateSpec getCommandInfo(EmbedCreateSpec spec);

    String[] getCommandCallers();
}
