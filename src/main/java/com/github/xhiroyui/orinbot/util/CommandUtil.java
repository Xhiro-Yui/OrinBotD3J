package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.datastore.dao.GuildPrefixRepository;
import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.spec.EmbedCreateSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CommandUtil {

	// Command Callers
	private static Map<String, Command> commandCallers = new HashMap<>();

	public void addCommand(Command command) {
		for (String caller : command.getAlias()) {
			commandCallers.put(caller, command);
		}
	}

	public Mono<? extends Command> commandLookup(String commandCaller) {
		return Mono.justOrEmpty(commandCaller)
				.flatMap(commandsName -> Mono.justOrEmpty(commandCallers.get(commandCaller)));
	}

	public EmbedCreateSpec generateCommandHelp(EmbedCreateSpec embedSpec, String commandName, String description) {
		return embedSpec.setTitle(commandName)
				.setDescription(description);
	}

	// Guild Prefixes
	@Autowired
	GuildPrefixRepository guildPrefixRepository;
	private static Map<Long, String> guildPrefixMap = new HashMap<>();

	public String getGuildPrefix(long guildId) {
		if (guildPrefixMap.containsKey(guildId)) {
			return guildPrefixMap.get(guildId);
		}
		return "~";
	}

	public void initializeGuildPrefixes() {
		guildPrefixRepository.findAll()
				.doOnNext(guildPrefix -> guildPrefixMap.put(guildPrefix.getGuildId(), guildPrefix.getPrefix()))
				.doFinally(signalType -> {
					if (guildPrefixMap.isEmpty())
						log.warn("Failed to initialize guild prefixes. All guilds will be using the default prefix [~].");
					else
						log.info("Guild prefix map initialization success");
				})
				.subscribe();
	}

//    public static void updateGuildPrefixes(DiscordClient client) {
//        TODO change to update from DB
//        client.getChannelById(Snowflake.of(BotConstant.LOG_CHANNEL_ID))
//                .cast(TextChannel.class)
//                .flatMap(chn -> chn.createMessage(spec -> spec.setContent("Unable to initialize guild prefix due to an SQL Exception.")));
//    }
}
