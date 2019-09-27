package com.github.xhiroyui.orinbot.util.dao;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.mysql.util.URLParser;
import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.nio.charset.StandardCharsets;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;

@Configuration
@EnableR2dbcRepositories
public class R2DBCConfig extends AbstractR2dbcConfiguration {
	private static final Logger log = LoggerFactory.getLogger(R2DBCConfig.class);

	@Override
	@Bean
	public ConnectionFactory connectionFactory() {
		return new JasyncConnectionFactory(new MySQLConnectionFactory(URLParser.INSTANCE.parseOrDie("mysql://vte8fokn6dm12nyh:ia0oj06877jgmmj6@otmaa16c1i9nwrek.cbetxkdyhwsb.us-east-1.rds.amazonaws.com/a08mb9fi8npzto7g", StandardCharsets.UTF_8)));
	}

}
