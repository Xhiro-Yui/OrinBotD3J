package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ErrorHandler {
    Mono<String> handle(Command object, Throwable error, MessageCreateEvent event);
}
