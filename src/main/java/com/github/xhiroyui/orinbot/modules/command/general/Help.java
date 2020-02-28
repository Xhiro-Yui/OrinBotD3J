package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class Help extends Command {
	public Help() {
		super("Provides information for commands (including this one). ",
				1,
				List.of("Command caller"),
				List.of("help", "h")
		);
	}

	@Override
	public Mono<Void> runCommand(MessageCreateEvent event, String[] args) {
		return Mono.just(event)
				.map(MessageCreateEvent::getMessage)
				.flatMap(Message::getChannel)
				.zipWith(commandUtil.commandLookup(args[0]))
				.flatMap(tuple -> tuple.getT1().createMessage(spec -> spec.setEmbed(embedSpec -> tuple.getT2().getCommandInfo(embedSpec))))
				.then();
	}
}
