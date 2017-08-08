package com.skcc.redis;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import com.skcc.redis.config.RedisConfig;

public class SessionInitializer extends AbstractHttpSessionApplicationInitializer{
    public SessionInitializer() {
        super(RedisConfig.class); 
    }
}
