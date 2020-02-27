package com.github.xhiroyui.orinbot.modules.command.administrator;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.command.RequiredPermissions;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.util.Permission;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredPermissions(Permission.ADMINISTRATOR)
public class Pong extends Command {
	public Pong() {
		super("Pings you back",
				1,
				List.of(),
				List.of("pong"));
	}

	@Override
	public Mono<Void> runCommand(MessageCreateEvent event, String[] args) {
		return Mono.just(event)
				.map(MessageCreateEvent::getMessage)
				.flatMap(Message::getChannel)
				.flatMap(channel -> channel.createMessage(spec -> spec.setContent("Ping " + args[0])))
				.then();
	}
}
