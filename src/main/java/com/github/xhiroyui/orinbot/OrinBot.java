package com.github.xhiroyui.orinbot;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrinBot {
    public static void main(String[] args) {
        final String TOKEN_VAJRA = System.getenv("OrinBotD3J_token");

        DiscordClient client = new DiscordClientBuilder(TOKEN_VAJRA).build();

        DiscordBotSetup setup = new DiscordBotSetup();
        setup.setupBot(client);


//        client.login().block();
    }


}
