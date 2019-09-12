package com.github.xhiroyui.orinbot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GUILD_PREFIX")
public class GuildPrefix {
	@Id
	@Column(name = "guild_id")
	private Long guildId;

	@Column(name = "prefix")
	private String prefix;


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
}
