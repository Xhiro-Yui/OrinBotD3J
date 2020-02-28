package com.github.xhiroyui.orinbot;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = OrinBotConfig.class)
public class OrinBot implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(OrinBot.class);

	@Autowired
	DiscordBotSetup setup;

	@Autowired
	GatewayDiscordClient gateway;

	public static void main(String[] args) {
		SpringApplication.run(OrinBot.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
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

		gateway.on(ReadyEvent.class)
				.subscribe(ready -> System.out.println("Logged in as " + ready.getSelf().getUsername()));

		setup.setupBot(gateway);
		System.out.println("Bla");
		gateway.onDisconnect().block();
	}
}
