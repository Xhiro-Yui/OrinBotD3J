package com.github.xhiroyui.orinbot.modules;

import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class Command {
    private String commandName;
    private String commandDescription;
    private int requiredParameters;
    private List<String> parameterDescription;
    private List<String> commandAlias;

    public Command(String description, int requiredParameters, List<String> parameterDescription, List<String> commandAlias) {
        this.commandName = getClass().getSimpleName();
        this.commandDescription = description;
        this.requiredParameters = requiredParameters;
        this.parameterDescription = parameterDescription;
        this.commandAlias = commandAlias;
    }

    protected abstract Mono<Void> executeCommand(MessageCreateEvent event, String args);

    protected abstract Mono<Void> runCommand(MessageCreateEvent event, String[] args);

    public EmbedCreateSpec getCommandInfo(EmbedCreateSpec spec) {
        return spec.setTitle(this.commandName)
                .setDescription(commandDescription);
    }

    protected Mono<String[]> processParameters(String arg) {
        return Mono.just(arg)
                .map(args -> args.equalsIgnoreCase("") ? BotUtil.EMPTY_ARRAY : args.split(" ", requiredParameters + 1))
                .filterWhen(args -> verifyParameterCount(args, requiredParameters))
                .switchIfEmpty(Mono.error(new CommandParameterCountException()))
                .filterWhen(this::validateParameters)
                .switchIfEmpty(Mono.error(new CommandParameterValidationException()))
                ;
    }

    protected Mono<Boolean> verifyParameterCount(String[] args, int requiredParameters) {
        if (args.length >= requiredParameters)
            return Mono.just(true);
        return Mono.empty();
    }

    protected Mono<Boolean> validateParameters(String[] args) {
        return Mono.just(true);
    }

    public List<String> getAlias() {
        return this.commandAlias;
    }
}
