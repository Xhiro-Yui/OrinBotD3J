package com.github.xhiroyui.orinbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("GUILD_PREFIX")
public class GuildPrefix {
	private @Id Long guildId;
	private String prefix;
}
