package cool.ange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author: ange
 * @package: cool.ange.config
 * @className: CorsConfig
 * @creationTime: 2023-03-24 14:53
 * @Version: v1.0
 * @description: 解决前端跨域问题，再后端配置跨问题
 */
@Configuration
public class CorsConfig {

    /**
     * 网关配置跨域
     *
     * @return CorsWebFilter 返回跨域的配置bean
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        //创建 CorsConfiguration 跨域配置信息对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //配置允许跨域访问的请求头信息， *表示所有的的都允许
        corsConfiguration.addAllowedHeader("*");
        //配置允许跨域访问的url，*表示所有的url都允许
        corsConfiguration.addAllowedOrigin("*");
        //配置允许跨域访问的请求方式，*表示有的请求方式都允许
        corsConfiguration.addAllowedMethod("*");

        //创建 UrlBasedCorsConfigurationSource 基于Url的Cors配置源对象
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        //记录上面配置的跨域设置
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        //创建 CorsWebFilter 对象，并将配置的跨域设置，作为参数设置到CorsWebFilter中
        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }
}
