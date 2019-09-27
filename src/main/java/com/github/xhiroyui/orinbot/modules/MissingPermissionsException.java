package com.github.xhiroyui.orinbot.modules;

import discord4j.core.object.entity.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MissingPermissionsException extends Exception {

    public MissingPermissionsException() {}

    public MissingPermissionsException(Member author) {
        super("Missing permissions exception");
    }
}
