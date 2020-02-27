package com.github.xhiroyui.orinbot.modules.command;

import discord4j.core.object.util.Permission;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequiredPermissions {
	Permission[] value() default {};
}
