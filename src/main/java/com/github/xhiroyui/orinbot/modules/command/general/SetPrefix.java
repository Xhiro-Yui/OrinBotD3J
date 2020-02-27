package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.datastore.dao.GuildPrefixDAO;
import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SetPrefix extends Command {

	@Autowired
	GuildPrefixDAO dao;

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
				.flatMap(channel -> dao.findAllByGivenPrefix("!").count().flatMap(count -> channel.createMessage(spec -> spec.setContent(count.toString()))))
				.then();
	}
}
