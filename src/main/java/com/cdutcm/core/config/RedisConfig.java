package com.cdutcm.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {


    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplateObject(RedisConnectionFactory factory) throws Exception {
        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
        redisTemplateObject.setConnectionFactory(factory);
        this.setSerializer(redisTemplateObject);//设置序列化方法
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }

    /**
     * 设置序列化方法
     */
    private void setSerializer(RedisTemplate<String, Object> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
    }


}
