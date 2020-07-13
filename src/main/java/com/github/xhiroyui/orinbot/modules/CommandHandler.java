package com.github.xhiroyui.orinbot.modules;

import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CommandHandler {

	@Autowired CommandUtil commandUtil;

	public Flux<Void> handleMCEvent(GatewayDiscordClient gateway) {
		return gateway.on(MessageCreateEvent.class)
				.filter(mce -> mce.getMessage().getAuthor().map(author -> !author.isBot()).orElse(false))
//				.filter(mce -> mce.getMessage().getContent().isPresent())
				.filter(mce -> mce.getGuildId().isPresent())
				.filter(this::checkPrefix)
				.flatMap(mce -> processCommand(trimCommand(mce), mce))
				.share();
	}

	private boolean checkPrefix(MessageCreateEvent mce) {
		log.debug("== Checking guild prefix in guild ID [{}] ==", mce.getGuildId().orElseThrow().asLong());
		log.debug("Guild prefix stored in memory is {}", commandUtil.getGuildPrefix(mce.getGuildId().orElseThrow()));
		return mce.getMessage().getContent().startsWith(commandUtil.getGuildPrefix(mce.getGuildId().orElseThrow()));
	}

	private String trimCommand(MessageCreateEvent mce) {
		return mce.getMessage().getContent().substring(commandUtil.getGuildPrefix(mce.getGuildId().orElseThrow()).length());
	}

	private Mono<Void> processCommand(String trimmedCommand, MessageCreateEvent mce) {
		final String[] splitCommand = trimmedCommand.split(" ", 2);
		return Flux.just(splitCommand[0])
				.flatMap(commandUtil::commandLookup)
				.flatMap(command -> command.executeCommand(mce, splitCommand.length == 1 ? "" : splitCommand[1])).then();
	}

}
