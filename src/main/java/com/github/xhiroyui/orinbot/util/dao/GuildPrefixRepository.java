package com.github.xhiroyui.orinbot.util.dao;

import com.github.xhiroyui.orinbot.entity.GuildPrefix;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import reactor.core.publisher.Flux;

//import javax.persistence.EntityManager;

public interface GuildPrefixRepository extends R2dbcRepository<GuildPrefix, Long> {

	@Query("SELECT * FROM GUILD_PREFIX GP WHERE GP.GUILD_ID = :GUILDID")
//	@Query("select id, firstname, lastname from customer c where c.lastname = :lastname")
	Flux<GuildPrefix> findMyVar(String var);

}
