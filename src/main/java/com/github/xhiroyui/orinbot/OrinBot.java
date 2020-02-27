package com.github.xhiroyui.orinbot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrinBot implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(OrinBot.class);

	@Autowired
	DiscordBotSetup setup;

	public static void main(String[] args) {
		SpringApplication.run(OrinBot.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		final String DEV_TOKEN = System.getenv("TOKEN_ORINBOT_DEV");

		GatewayDiscordClient gateway = DiscordClient.create(DEV_TOKEN).login().block();

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

		assert gateway != null;

		gateway.on(ReadyEvent.class)
				.subscribe(ready -> System.out.println("Logged in as " + ready.getSelf().getUsername()));

		setup.setupBot(gateway);

		gateway.onDisconnect().block();
	}
}
