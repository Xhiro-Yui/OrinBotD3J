//package com.github.xhiroyui.orinbot.modules.command.general;
//
//import com.github.xhiroyui.orinbot.util.dao.GuildPrefixDao;
//import discord4j.core.event.domain.message.MessageCreateEvent;
//import discord4j.core.object.entity.Message;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//public class SetPrefix extends GeneralCommands {
//    private GuildPrefixDao dao ;
//
//    public SetPrefix() {
//        super("Updates the prefix for this guild.",
//                1,
//                List.of("Input prefix"),
//                List.of("setprefix", "prefix"));
//    }
//
//    @Override
//    public Mono<Void> runCommand(MessageCreateEvent event, String[] args) {
////        return Mono.just(event)
////                .filterWhen(ignored -> GuildPrefixDao.createUpdateGuildPrefix(event.getGuildId().get().asLong(), args[0]))
////                .switchIfEmpty(event.getMessage().getChannel().createMessag)
////                .flatMap(message -> )
////                .map(MessageCreateEvent::getMessage)
////                .flatMap(Message::getChannel)
////                .flatMap(channel -> channel.createMessage(spec -> spec.setContent("Pong")))
////                .then();
//        return null;
//    }
//}
