package com.skcc.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60) // set timeout 1min
public class RedisConfig {
	@Autowired
	private JedisConnectionFactory connectionFactory;
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
	
//	 @Bean
//     public LettuceConnectionFactory connectionFactory() {
//             return new LettuceConnectionFactory(); 
//     }
}
