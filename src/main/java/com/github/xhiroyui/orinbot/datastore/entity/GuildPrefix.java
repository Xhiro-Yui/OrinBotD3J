package com.github.xhiroyui.orinbot.datastore.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("guild_prefix")
public class GuildPrefix {

	@Id
	private Long guildId;
	private String prefix;

	public GuildPrefix(Long guildId, String prefix) {
		this.guildId = guildId;
		this.prefix = prefix;
	}

	public Long getGuildId() {
		return guildId;
	}

	public void setGuildId(Long guildId) {
		this.guildId = guildId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String toString() {
		return "[Guild Id : " + this.guildId + " | Prefix : " + this.prefix + "]";
	}
}
