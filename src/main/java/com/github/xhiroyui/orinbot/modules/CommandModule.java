package com.github.xhiroyui.orinbot.modules;

import reactor.core.publisher.Mono;

public abstract class CommandModule {

    public Mono<String[]> processParameters(String[] arg, int requiredParameters) {
        return Mono.just(arg)
                .filterWhen(args -> verifyParameterCount(args, requiredParameters))
                .switchIfEmpty(Mono.error(new CommandParameterCountException()))
                .filterWhen(this::validateParameters)
                .switchIfEmpty(Mono.error(new CommandParameterValidationException()))
                ;
    }

    public Mono<Boolean> verifyParameterCount(String[] args, int requiredParameters) {
        if (args.length == requiredParameters)
            return Mono.just(true);
        return Mono.empty();
    }

    public Mono<Boolean> validateParameters(String[] args) {
        return Mono.just(true);
    }
}