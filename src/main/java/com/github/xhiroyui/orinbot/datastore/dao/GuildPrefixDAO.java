package com.github.xhiroyui.orinbot.datastore.dao;

import com.github.xhiroyui.orinbot.datastore.entity.GuildPrefix;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GuildPrefixDAO extends ReactiveCrudRepository<GuildPrefix, Long> {

	@Query("select * from guild_prefix where prefix = :givenPrefix")
	Flux<GuildPrefix> findAllByGivenPrefix(String givenPrefix);

}
