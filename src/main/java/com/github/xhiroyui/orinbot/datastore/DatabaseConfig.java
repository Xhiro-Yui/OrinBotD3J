package com.github.xhiroyui.orinbot.datastore;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Duration;

@Configuration
@EnableR2dbcRepositories
public class DatabaseConfig extends AbstractR2dbcConfiguration {

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ConnectionPool(ConnectionPoolConfiguration.builder(MySqlConnectionFactory
				.from(MySqlConnectionConfiguration
						.builder()
//						.host("127.0.0.1")
//						.username("root")
//						.password("abc123")
//						.database("orinbot")
						.host("otmaa16c1i9nwrek.cbetxkdyhwsb.us-east-1.rds.amazonaws.com")
						.username("vte8fokn6dm12nyh")
						.password("ia0oj06877jgmmj6")
						.database("a08mb9fi8npzto7g")
						.port(3306)
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
