package com.github.xhiroyui.orinbot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DiscordGatewayInitializer {

	@Autowired
	DiscordGatewayConfig config;

	@Bean
	public GatewayDiscordClient gateway() {
		log.info("Creating the discord gateway.");
		return DiscordClient.create(config.getToken()).login().block();
	}
}
