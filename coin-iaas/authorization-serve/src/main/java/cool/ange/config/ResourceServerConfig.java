package cool.ange.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author: ange
 * @package: cool.ange.config
 * @className: ResourceServerConfig
 * @creationTime: 2023-02-19 13:02
 * @Version: v1.0
 * @description: 资源服务器配置，系统鉴权服务器本身也是受保护的访问资源
 */

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
}
