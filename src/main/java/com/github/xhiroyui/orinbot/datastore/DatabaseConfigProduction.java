package com.github.xhiroyui.orinbot.datastore;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Duration;

@Configuration
@EnableR2dbcRepositories
@Profile("production")
public class DatabaseConfigProduction extends AbstractR2dbcConfiguration {

	@Value("${ORINBOT_DB_HOST}")
	private String host;

	@Value("${ORINBOT_DB_USERNAME}")
	private String username;

	@Value("${ORINBOT_DB_PASSWORD}")
	private String password;

	@Value("${ORINBOT_DB_DATABASE}")
	private String database;

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ConnectionPool(ConnectionPoolConfiguration.builder(MySqlConnectionFactory
				.from(MySqlConnectionConfiguration
						.builder()
						.host(host)
						.username(username)
						.password(password)
						.database(database)
						.build()
				))
				.maxIdleTime(Duration.ofSeconds(600)) // 10 minutes
				.initialSize(2)
				.maxSize(8)
				.build());
	}

	@Bean
	public DatabaseClient databaseClient() {
		return DatabaseClient.create(connectionFactory());
	}
}
