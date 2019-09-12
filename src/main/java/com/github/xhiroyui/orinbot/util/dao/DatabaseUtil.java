package com.github.xhiroyui.orinbot.util.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtil {
	private static final Logger log = LoggerFactory.getLogger(DatabaseUtil.class);
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.github.xhiroyui.orinbot", getProperties());

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	private static Map getProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.hikari.username", System.getenv("orinBotD3J_username"));
		properties.put("hibernate.hikari.password", System.getenv("orinBotD3J_pwd"));
		return properties;
	}

}
