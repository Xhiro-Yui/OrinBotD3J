package com.github.xhiroyui.orinbot.util.dao;

import com.github.xhiroyui.orinbot.entity.GuildPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class GuildPrefixDao extends DatabaseUtil {
	private static final Logger log = LoggerFactory.getLogger(GuildPrefixDao.class);

	public void updateGuildPrefix(Long guildId, String newPrefix) {
		EntityManager em = getEntityManager();

		GuildPrefix gp = new GuildPrefix();
		gp.setGuildId(guildId);
		gp.setPrefix(newPrefix);
		try {
			em.getTransaction().begin();
			em.persist(gp);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.warn("Transaction failed.");
		} finally {
			em.close();
		}
	}
}
