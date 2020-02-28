package com.github.xhiroyui.orinbot.datastore;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Duration;

@Configuration
@EnableR2dbcRepositories
public class DatabaseInitializer extends AbstractR2dbcConfiguration {

	@Autowired
	DatabaseConfig config;

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ConnectionPool(ConnectionPoolConfiguration.builder(MySqlConnectionFactory
				.from(MySqlConnectionConfiguration
						.builder()
						.host(config.getHost())
						.username(config.getUsername())
						.password(config.getPassword())
						.database(config.getDatabase())
						.build()
				))
				.maxIdleTime(Duration.ofSeconds(30000))
				.initialSize(config.getInitialSize())
				.maxSize(config.getMaxSize())
				.build());
	}

	@Bean
	public DatabaseClient databaseClient() {
		return DatabaseClient.create(connectionFactory());
	}
}
