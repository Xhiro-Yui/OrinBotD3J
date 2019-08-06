package com.github.xhiroyui.orinbot.modules.command.general;

import com.github.xhiroyui.orinbot.modules.Command;

import java.util.List;

abstract class GeneralCommands extends Command {
    GeneralCommands(String description, int requiredParameters, List<String> parameterDescription, List<String> commandAlias) {
        super(description, requiredParameters, parameterDescription, commandAlias);
    }
}
