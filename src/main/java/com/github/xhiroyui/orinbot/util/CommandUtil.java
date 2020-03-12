package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.datastore.dao.GuildPrefixRepository;
import com.github.xhiroyui.orinbot.datastore.entity.GuildPrefix;
import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.EmbedCreateSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Component
public class CommandUtil {

	@Autowired Collection<Command> commandList;
	@Autowired GuildPrefixRepository guildPrefixRepository;

	private static Map<String, Command> commandCallers = new HashMap<>();
	private static Map<Snowflake, String> guildPrefixMap = new HashMap<>();
	private static List<Snowflake> whitelistedUserList = new ArrayList<>();

	@PostConstruct
	private void addCommands() {
		for (Command command : commandList)
			for (String caller : command.getAlias()) commandCallers.put(caller, command);
	}

	public Flux<GuildPrefix> initializeGuildPrefixes() {
		log.info("[PreLoginSetup - Guild Prefixes] Initializing.");
		return guildPrefixRepository.findAll()
				.doOnNext(guildPrefix -> {
					log.debug("[-- Guild Prefixes --] Adding guild prefix of {} with prefix of {} into map.", guildPrefix.getGuildId(), guildPrefix.getPrefix());
					guildPrefixMap.put(Snowflake.of(guildPrefix.getGuildId()), guildPrefix.getPrefix());
				})
				.doFinally(signalType -> {
					if (guildPrefixMap.isEmpty())
						log.warn("[PreLoginSetup - Guild Prefixes] Failed to load guild prefixes. All guilds will be using the default prefix [~].");
					else
						log.info("[PreLoginSetup - Guild Prefixes] Initialization complete.");
				});
	}

	public Flux<Snowflake> initializeUserWhitelist(String[] users) {
		if (users == null || users.length < 1) {
			log.info("[PreLoginSetup - User Whitelist] Initialization skipped as no user IDs were declared." );
			return Flux.empty();
		}
		else {
			log.info("[PreLoginSetup - User Whitelist] Initializing." );
			return Flux.fromArray(users)
					.map(Snowflake::of)
					.doOnNext(userSnowflake -> {
						log.debug("[-- User Whitelist --] Adding user {} into whitelist.", userSnowflake);
						whitelistedUserList.add(userSnowflake);
					})
					.doFinally(signalType -> log.info("[PreLoginSetup - User Whitelist] Whitelisting of {} users complete.", whitelistedUserList.size()));
		}
	}

	public Boolean ownerBypassCheck(Snowflake user) {
		return whitelistedUserList.contains(user);
	}

//	public Boolean ownerBypassCheck(Member user) {
//		return whitelistedUserList.contains(user.getId());
//	}


	public Mono<? extends Command> commandLookup(String commandCaller) {
		return Mono.justOrEmpty(commandCaller)
				.flatMap(commandsName -> Mono.justOrEmpty(commandCallers.get(commandCaller)));
	}

	public EmbedCreateSpec generateCommandHelp(EmbedCreateSpec embedSpec, String commandName, String description) {
		return embedSpec.setTitle(commandName)
				.setDescription(description);
	}

	public String getGuildPrefix(Snowflake guildId) {
		if (guildPrefixMap.containsKey(guildId)) {
			return guildPrefixMap.get(guildId);
		}
		return "~";
	}
//    public static void updateGuildPrefixes(DiscordClient client) {
//        TODO change to update from DB
//        client.getChannelById(Snowflake.of(BotConstant.LOG_CHANNEL_ID))
//                .cast(TextChannel.class)
//                .flatMap(chn -> chn.createMessage(spec -> spec.setContent("Unable to initialize guild prefix due to an SQL Exception.")));
//    }
}
