package com.github.xhiroyui.orinbot;

import com.github.xhiroyui.orinbot.config.DiscordGatewayConfig;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = OrinBotConfig.class)
public class OrinBot implements CommandLineRunner {

	@Autowired
	DiscordBotSetup setup;

	@Autowired
	DiscordGatewayConfig config;

	public static void main(String[] args) {
		SpringApplication.run(OrinBot.class, args);
	}

	@Override
	public void run(String... args) {
		// For advanced use when Sharding is necessary
//        GatewayDiscordClient gateway = DiscordClient.create(DEV_TOKEN).gateway()
//                .setInitialPresence(shard -> Presence.online())
//                .setSharding(ShardingStrategy.recommended())
//                .setShardCoordinator(new LocalShardCoordinator())
//                .setAwaitConnections(true)
//                .setStoreService(new JdkStoreService())
//                .setEventDispatcher(EventDispatcher.buffering())
//                .connect()
//                .block();
		GatewayDiscordClient gateway = Mono.when(
				setup.initializeGuildPrefixes(),
				setup.initializeWhitelist()
		) // Add more preLoginSetup to this list of Mono.when()
				.then(DiscordClient.create(config.getToken()).login())
				.flatMap(gatewayClient -> setup.setupCommands(gatewayClient)).block();

		Objects.requireNonNull(gateway).onDisconnect().block();
	}
}
