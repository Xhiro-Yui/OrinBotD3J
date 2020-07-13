package com.github.xhiroyui.orinbot.modules;

public class CommandParameterValidationException extends Exception {

    public CommandParameterValidationException(String guildPrefix, String commandName, String inputParameter) {
        super("Incorrect parameters in **" + commandName + "** command. `" + inputParameter + "` is not a valid parameter.  Use `" + guildPrefix + "help` command for more info regarding the required command parameters.");
    }

}
