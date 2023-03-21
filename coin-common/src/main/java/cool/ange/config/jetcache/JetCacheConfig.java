package cool.ange.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: ange
 * @package: cool.ange.config.jetcache
 * @className: JetCacheConfig
 * @creationTime: 2023-03-05 09:35
 * @Version: v1.0
 * @description: JetCache配置类 ，开启JetCache注解
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "cool.ange.service.impl")
public class JetCacheConfig {

}

