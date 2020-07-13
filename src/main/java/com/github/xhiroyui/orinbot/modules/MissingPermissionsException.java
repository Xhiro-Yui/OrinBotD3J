package com.github.xhiroyui.orinbot.modules;

import discord4j.core.object.entity.User;
import discord4j.rest.util.Permission;

public class MissingPermissionsException extends Exception {

    public MissingPermissionsException(User user, Permission p, String commandName) {
        super(user.getMention() + " does not have the required permissions [" + p.name() + "] to use command **" + commandName + "**");
    }
}
