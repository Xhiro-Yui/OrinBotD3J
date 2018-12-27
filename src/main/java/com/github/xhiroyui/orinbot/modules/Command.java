package com.github.xhiroyui.orinbot.modules;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public interface Command {
    public abstract Mono<Void> executeCommand(MessageCreateEvent event, String[] args);

    public Mono<Void> runCommand(MessageCreateEvent event, String[] args);

    /**
     * Provides information to the user regarding the command
     * @param event Event where this command was executed from
     * @param args Command arguments
     * @return Mono.empty() which signals completion of this command
     */
    public Mono<Void> getCommandInfo(MessageCreateEvent event, String[] args);

    public String[] getCommandCallers();
}
