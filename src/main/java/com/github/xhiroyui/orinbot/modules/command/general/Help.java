package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.util.BotUtil;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.List;

public class Help extends GeneralCommands {
    public Help() {
        super("Provides information for commands (including this one). ",
                1,
                List.of("Command caller"),
                List.of("help", "h")
        );
    }

    @Override
    public Mono<Void> executeCommand(MessageCreateEvent event, String args) {
        return Mono.justOrEmpty(event)
                .flatMap(ignored -> processParameters(args))
                .onErrorResume(error -> BotUtil.COMMAND_ERROR_HANDLER.handle(this, error, event)
                        .flatMap(errorMessage -> event.getMessage().getChannel()
                                .flatMap(channel -> channel.createMessage(spec -> spec.setContent(errorMessage))))
                        .then(Mono.empty()))
                .flatMap(processedArgs -> runCommand(event, processedArgs))
                .then();
    }

    @Override
    public Mono<Void> runCommand(MessageCreateEvent event, String[] args) {
        return Mono.just(event)
                .map(MessageCreateEvent::getMessage)
                .flatMap(Message::getChannel)
                .zipWith(CommandUtil.commandLookup(args[0]))
                .flatMap(tuple -> tuple.getT1().createMessage(spec -> spec.setEmbed(embedSpec -> tuple.getT2().getCommandInfo(embedSpec))))
                .then();
    }
}
