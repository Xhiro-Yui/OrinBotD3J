package com.github.xhiroyui.orinbot.modules;

public class CommandParameterCountException extends Exception {

    public CommandParameterCountException(String guildPrefix, int requiredParameters, String commandName) {
        super("Insufficient parameters given for the command **" + commandName + "**. Please check the required parameters for the command by using the help function `" + guildPrefix + "help " + commandName + "`");
    }

}
