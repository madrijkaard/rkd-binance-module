package com.rkd.binance.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class responsible for Cassandra database configuration settings.
 */
@Configuration
public class CassandraConfig {
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Bean
    public CqlSession session() {
        return CqlSession.builder().withKeyspace(keyspaceName).build();
    }
}
