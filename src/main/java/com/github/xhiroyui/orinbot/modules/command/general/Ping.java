package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class Ping extends Command {
	public Ping() {
		super("Pongs you.",
				0,
				BotUtil.generateCommandDescriptionMap(Collections.emptyList(), Collections.emptyList()),
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
