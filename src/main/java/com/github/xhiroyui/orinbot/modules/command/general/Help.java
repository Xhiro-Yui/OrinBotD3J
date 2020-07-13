package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;
import com.github.xhiroyui.orinbot.modules.CommandParameterValidationException;
import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class Help extends Command {
	public Help() {
		super("Provides information for commands (including this one). ",
				0,
				BotUtil.generateCommandDescriptionMap(List.of("a"), List.of("a")),
				List.of("help", "h")
		);
	}

	@Override
	public Mono<Void> runCommand(MessageCreateEvent event, String[] args) {
		Map.of("S", "s");
		return Mono.just(event)
				.map(MessageCreateEvent::getMessage)
				.flatMap(Message::getChannel)
				.flatMap(messageChannel -> {
							if (args.length == 0)
								return messageChannel.createMessage(messageCreateSpec -> messageCreateSpec.setContent("BLA"));

							return commandUtil.commandLookup(args[0]).flatMap(command ->
									messageChannel.createMessage(messageCreateSpec ->
											messageCreateSpec.setEmbed(command::getCommandInfo)
									)
							);
						}
				)
				.then();
	}

	@Override
	protected Mono<Boolean> validateParameters(MessageCreateEvent mce, String[] args) {
		if (args.length == 1) {
			return commandUtil.commandLookup(args[0])
					.switchIfEmpty(Mono.error(new CommandParameterValidationException(
									commandUtil.getGuildPrefix(mce.getGuildId().orElseThrow()),
									this.getClass().getSimpleName(),
									args[0])
							)
					)
					.then(Mono.just(true));
		}
		return super.validateParameters(mce, args);
	}
}
