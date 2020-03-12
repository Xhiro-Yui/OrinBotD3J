package com.github.xhiroyui.orinbot.modules;

public class CommandParameterValidationException extends Exception {

    public CommandParameterValidationException(String commandName) {
        super("Incorrect parameters in **" + commandName + "** command. Use `help` command for more info regarding the required command parameters.");
    }

}
