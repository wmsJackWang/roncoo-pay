package com.sohu.config;  
import com.fasterxml.jackson.annotation.JsonAutoDetect;  
import com.fasterxml.jackson.annotation.PropertyAccessor;  
import com.fasterxml.jackson.databind.ObjectMapper;  
import org.springframework.cache.CacheManager;  
import org.springframework.cache.annotation.CachingConfigurerSupport;  
import org.springframework.cache.annotation.EnableCaching;  
import org.springframework.cache.interceptor.KeyGenerator;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.data.redis.cache.RedisCacheManager;  
import org.springframework.data.redis.connection.RedisConnectionFactory;  
import org.springframework.data.redis.core.RedisTemplate;  
import org.springframework.data.redis.core.StringRedisTemplate;  
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;  
import java.lang.reflect.Method;  
/**
 * 
 * @类名    RedisConfig
 * @功能描述 TODO
 * @创建人      王洪会
 * @创建时间 2016年11月18日 下午12:46:59
 * @version V1.0
 *
 */
@Configuration  
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{  
  
    @Bean  
    public KeyGenerator wiselyKeyGenerator(){  
        return new KeyGenerator() {  
            @Override  
            public Object generate(Object target, Method method, Object... params) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(target.getClass().getName());  
                sb.append(method.getName());  
                for (Object obj : params) {  
                    sb.append(obj.toString());  
                }  
                return sb.toString();  
            }  
        };  
  
    }  
  
    @Bean  
    public CacheManager cacheManager(  
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {  
        return new RedisCacheManager(redisTemplate);  
    }  
  
    @Bean  
    public RedisTemplate<String, Object> redisTemplate(  
            RedisConnectionFactory factory) {  
    	Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;  
    }  
}  