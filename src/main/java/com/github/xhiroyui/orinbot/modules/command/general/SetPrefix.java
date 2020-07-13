package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.datastore.dao.GuildPrefixRepository;
import com.github.xhiroyui.orinbot.datastore.entity.GuildPrefix;
import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class SetPrefix extends Command {

	@Autowired
	GuildPrefixRepository dao;

	@Autowired
	DatabaseClient db;

	public SetPrefix() {
		super("Updates the prefix for this guild.",
				1,
				BotUtil.generateCommandDescriptionMap(Collections.emptyList(), Collections.emptyList()),
				List.of("setprefix"));
	}

	public Mono<Void> runCommand(MessageCreateEvent event, String[] args) {
		return Mono.just(event)
				.map(MessageCreateEvent::getMessage)
				.flatMap(Message::getChannel)
				.flatMap(channel -> db.select()
						.from(GuildPrefix.class)
						.fetch()
						.all()
						.count()
						.flatMap(count -> channel
								.createMessage(spec -> spec.setContent("Count from DB : " + count.toString()))))
				.then();
	}

}
