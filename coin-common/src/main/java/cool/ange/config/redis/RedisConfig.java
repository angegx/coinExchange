package cool.ange.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: ange
 * @package: cool.ange.config.redis
 * @className: RedisConfig
 * @creationTime: 2023-03-05 09:54
 * @Version: v1.0
 * @description: redis相关配置类
 */

@Configuration
public class RedisConfig {

    /**
     * RedisTemplate
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //配置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // redis key的序列化
        StringRedisSerializer keyRedisSerializer = new StringRedisSerializer();
        // redis value的序列化
        GenericJackson2JsonRedisSerializer valueRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //配置key的序列化
        redisTemplate.setKeySerializer(keyRedisSerializer);
        //设置value的序列化
        redisTemplate.setValueSerializer(valueRedisSerializer);
        //设置hashKey的序列化
        redisTemplate.setHashKeySerializer(keyRedisSerializer);
        //设置hashValue的序列化
        redisTemplate.setHashValueSerializer(valueRedisSerializer);
        return redisTemplate ;
    }
}
