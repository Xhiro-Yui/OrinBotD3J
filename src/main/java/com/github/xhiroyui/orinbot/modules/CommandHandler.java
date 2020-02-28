package com.github.xhiroyui.orinbot.modules;

import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class CommandHandler {
	@Autowired CommandUtil commandUtil;
	private final GatewayDiscordClient gateway;
	private Map<Long, String> guildPrefixes;

	public CommandHandler(GatewayDiscordClient gateway) {
		this.gateway = gateway;
//        CommandUtil.initializeGuildPrefixes();
	}

	public Flux<Void> handleMCEvent() {
		return gateway.on(MessageCreateEvent.class)
				.filter(mce -> mce.getMessage().getContent().isPresent())
				.filter(mce -> mce.getMessage().getAuthor().map(author -> !author.isBot()).orElse(false))
				.filter(this::checkPrefix)
				.flatMap(mce -> processCommand(trimCommand(mce), mce))
				.share();
	}

	private boolean checkPrefix(MessageCreateEvent mce) {
		return mce.getMessage().getContent().get().startsWith(commandUtil.getGuildPrefix(mce.getGuildId().get().asLong()));
	}

	private String trimCommand(MessageCreateEvent mce) {
		return mce.getMessage().getContent().get().substring(commandUtil.getGuildPrefix(mce.getGuildId().get().asLong()).length());
	}

	private Mono<Void> processCommand(String trimmedCommand, MessageCreateEvent mce) {
		final String[] splittedCommand = trimmedCommand.split(" ", 2);
		return Flux.just(splittedCommand[0])
				.flatMap(commandUtil::commandLookup)
				.flatMap(command -> command.executeCommand(mce, splittedCommand.length == 1 ? "" : splittedCommand[1])).then();
	}

}
