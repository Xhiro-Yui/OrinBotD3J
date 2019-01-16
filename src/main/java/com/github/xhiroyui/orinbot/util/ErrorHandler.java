package com.github.xhiroyui.orinbot.util;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ErrorHandler {
    Mono<String> handle(Object object, Throwable error, MessageCreateEvent event);
}
