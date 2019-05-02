package com.github.xhiroyui.orinbot.modules;

import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.util.Map;

public class CommandHandler {
    private final DiscordClient client;
    private Map<Long, String> guildPrefixes;

    public CommandHandler(DiscordClient client) {
        this.client = client;
        CommandUtil.initializeGuildPrefixes();
    }

    public Flux<Void> handleMCEvent() {
        return client.getEventDispatcher()
                .on(MessageCreateEvent.class)
                .filter(mce -> mce.getMessage().getContent().isPresent())
                .filterWhen(mce -> mce.getMessage().getAuthor().map(author -> !author.isBot()))
                .map(this::checkPrefixAndTrim)
                .filter(Tuple3::getT1)
                .flatMap(tup3 -> processCommand(tup3.getT2(), tup3.getT3()))
                .share();
    }

    private Tuple3<Boolean, String, MessageCreateEvent> checkPrefixAndTrim(MessageCreateEvent mce) {
        return Tuples.of(
                mce.getMessage().getContent().get().startsWith(CommandUtil.getGuildPrefix(mce.getGuildId().get().asLong())),
                mce.getMessage().getContent().get().substring(CommandUtil.getGuildPrefix(mce.getGuildId().get().asLong()).length()),
                mce);
    }

    private Mono<Void> processCommand(String trimmedCommand, MessageCreateEvent mce) {
        final String[] splittedCommand = trimmedCommand.split(" ", 2);
        return Flux.just(splittedCommand[0])
                .flatMap(CommandUtil::commandLookup)
                .flatMap(command -> command.executeCommand(mce, splittedCommand.length == 1 ? "":splittedCommand[1])).then();
    }

}
