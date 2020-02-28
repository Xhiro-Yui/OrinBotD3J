package com.github.xhiroyui.orinbot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("bot")
public class DiscordGatewayConfig implements OrinBotConfig {

	@NonNull private String token;

}
