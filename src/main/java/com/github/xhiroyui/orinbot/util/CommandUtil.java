package com.github.xhiroyui.orinbot.util;

import com.github.xhiroyui.orinbot.modules.Command;
import discord4j.core.spec.EmbedCreateSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

//import discord4j.core.object.entity.TextChannel;

public class CommandUtil {
    private static final Logger log = LoggerFactory.getLogger(CommandUtil.class);

    // Command Callers
    private static Map<String, Command> commandCallers = new HashMap<>();

    public static void addCommand(Command command) {
        for (String caller : command.getAlias()) {
            commandCallers.put(caller, command);
        }
    }

    public static Mono<? extends Command> commandLookup(String commandCaller) {
        return Mono.justOrEmpty(commandCaller)
                .flatMap(commandsName -> Mono.justOrEmpty(commandCallers.get(commandCaller)));
    }

    public static EmbedCreateSpec generateCommandHelp(EmbedCreateSpec embedSpec, String commandName, String description) {
        return embedSpec.setTitle(commandName)
                .setDescription(description);
    }

    // Guild Prefixes
    private static Map<Long, String> guildPrefixes = new HashMap<>();

    public static String getGuildPrefix(long guildId) {
        if (guildPrefixes.containsKey(guildId)) {
            return guildPrefixes.get(guildId);
        }
        return "~";
    }

//    public static void initializeGuildPrefixes() {
//        EntityManager em = DatabaseUtil.getEntityManager();
//        try {
//            List<GuildPrefix> listGp = em.createQuery("select a from "+GuildPrefix.class.getSimpleName()+" a", GuildPrefix.class).getResultList();
//            for (GuildPrefix gp : listGp) {
//                guildPrefixes.put(gp.getGuildId(), gp.getPrefix());
//            }
//        } catch (Exception e){
//            log.warn("Failed to initialize guild prefixes. All guilds will be using the default prefix [~].");
//        } finally {
//            em.close();
//        }
//    }

//    public static void updateGuildPrefixes(DiscordClient client) {
//        TODO change to update from DB
//        client.getChannelById(Snowflake.of(BotConstant.LOG_CHANNEL_ID))
//                .cast(TextChannel.class)
//                .flatMap(chn -> chn.createMessage(spec -> spec.setContent("Unable to initialize guild prefix due to an SQL Exception.")));
//    }
}
