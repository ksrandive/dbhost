package com.ks.dbHost.config;

import com.ks.dbHost.model.Person;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.pool.max-active}")
    private int redisPoolMaxActive;

    @Value("${spring.redis.pool.max-wait}")
    private int redisPoolMaxWait;

    @Value("${spring.redis.pool.max-idle}")
    private int redisPoolMaxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int redisPoolMinIdle;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.timeout}")
    private int redisConnectionTimeout;

    @Value("${spring.redis.database.person}")
    private int personDatabase;

    @Bean
    public RedisTemplate<String, List<String>> personRedisTemplate() {
        RedisTemplate<String, List<String>> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(GetLettuceConnectionFactory(redisHost, redisPort, personDatabase, redisConnectionTimeout));
        return template;
    }

    public LettuceConnectionFactory GetLettuceConnectionFactory(String redisHost, int redisPort, int database, int timeOut) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        //redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeOut))
                .poolConfig(getGenericObjectPoolConfig())
                .build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    public GenericObjectPoolConfig<RedisConnection> getGenericObjectPoolConfig() {
        GenericObjectPoolConfig<RedisConnection> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxIdle(redisPoolMaxIdle);
        genericObjectPoolConfig.setMinIdle(redisPoolMinIdle);
        genericObjectPoolConfig.setMaxTotal(redisPoolMaxActive);
        genericObjectPoolConfig.setMaxWaitMillis(redisPoolMaxWait);
        return genericObjectPoolConfig;
    }
}
