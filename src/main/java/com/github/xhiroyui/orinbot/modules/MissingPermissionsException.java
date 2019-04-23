package com.github.xhiroyui.orinbot.modules;

import discord4j.core.object.entity.Member;

public class MissingPermissionsException extends Exception {

    public MissingPermissionsException() {}

    public MissingPermissionsException(Member author) {
        super("Missing permissions exception");
    }
}
