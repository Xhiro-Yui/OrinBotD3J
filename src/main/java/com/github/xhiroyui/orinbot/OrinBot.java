package com.github.xhiroyui.orinbot;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrinBot {

    private static final Logger logger = LoggerFactory.getLogger(OrinBot.class);

    public static void main(String[] args) {
        final String TOKEN_VAJRA = "NDA1OTk5Mzg0NjY5NzE2NDgw.Dkqh9w.T17ocB5BoHYX3P4XIjAca7Fb13c";

        DiscordClient client = new DiscordClientBuilder(TOKEN_VAJRA).build();

        DiscordBotSetup setup = new DiscordBotSetup();
        setup.setupBot(client);

        client.login().block();
    }


}