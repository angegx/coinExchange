package cool.ange.config.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author: ange
 * @package: cool.ange.config.jackson
 * @className: JacksonConfig
 * @creationTime: 2023-03-05 09:33
 * @Version: v1.0
 * @description: Jackson配置类
 */

@Configuration
public class JacksonConfig {

    /**
     * 配置ObjectMapper
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        //创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        //设置1.8新添加的时间相关类配置
        objectMapper.registerModule(new JavaTimeModule());
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        //设置时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        //设置日期时间格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }


    /**
     * 更换redis的序列化形式为Jackson
     *
     * @param objectMapper  objectMapper
     * @return RedisSerializer
     */
    @Bean
    public RedisSerializer<Object> redisSerializer(ObjectMapper objectMapper) {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //设置setObjectMapper
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

}
