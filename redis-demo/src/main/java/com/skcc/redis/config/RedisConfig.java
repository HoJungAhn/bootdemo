package com.skcc.redis.config;

import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.client.IOSClientBuilder.V3;
import org.openstack4j.api.storage.ObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
	@Autowired
	private JedisConnectionFactory connectionFactory;
//	@Autowired
//	private V3 objectStorage;
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}

//	@Bean
//	public ObjectStorageService objectStorage(){
//		OSClientV3 authenticate = objectStorage.authenticate();
//		System.out.println("Authenticated successfully!");
//		return authenticate.objectStorage();
//	}

}
