package com.github.xhiroyui.orinbot.datastore;

import com.github.xhiroyui.orinbot.OrinBotConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("db")
public class DatabaseConfig implements OrinBotConfig {
	private String host;

	public DatabaseConfig(String host, String username, String password, String database, int initialSize, int maxSize) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.database = database;
		this.initialSize = initialSize;
		this.maxSize = maxSize;
	}

	private String username;
	private String password;
	private String database;
	private int initialSize;
	private int maxSize;

	public int getInitialSize() {
		return initialSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getDatabase() {
		return database;
	}
}
