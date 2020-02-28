package com.github.xhiroyui.orinbot.datastore.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("guild_prefix")
public class GuildPrefix {

	@Id
	private Long guildId;
	private String prefix;
}
