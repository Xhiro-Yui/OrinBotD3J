package com.github.xhiroyui.orinbot.datastore;

import com.github.xhiroyui.orinbot.OrinBotConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("db")
public class DatabaseConfig implements OrinBotConfig {

	@NonNull private String host;
	@NonNull private String username;
	@NonNull private String password;
	@NonNull private String database;
	private int initialSize;
	private int maxSize;
}
