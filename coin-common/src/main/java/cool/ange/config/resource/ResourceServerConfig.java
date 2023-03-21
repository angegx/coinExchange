package cool.ange.config.resource;

import com.google.common.annotations.Beta;
import cool.ange.config.jackson.JacksonConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author: ange
 * @package: cool.ange.config.resource
 * @className: ResourceServerConfig
 * @creationTime: 2023-03-05 10:08
 * @Version: v1.0
 * @description: 资源服务器相关配置类
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 配置不需要权限也可以访问的资源路径
     *
     * @param http 不需要权限也可以访问的资源路径
     * @throws Exception Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement().disable()
                .authorizeRequests()
                .antMatchers(
                        "/markets/kline/**" ,
                        "/users/setPassword" ,
                        "/users/register",
                        "/sms/sendTo",
                        "/gt/register" ,
                        "/login" ,
                        "/v2/api-docs",
                        "/swagger-resources/configuration/ui",//用来获取支持的动作
                        "/swagger-resources",//用来获取api-docs的URI
                        "/swagger-resources/configuration/security",//安全选项
                        "/webjars/**",
                        "/",
                        "/csrf",
                        "/swagger-ui.html"
                ).permitAll()
                .antMatchers("/**").authenticated()
                .and().headers().cacheControl();
    }

    /**
     * 设置公钥
     *
     * @param resources 安全配置资源
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //设置token的存储方式为jwt
        resources.tokenStore(jwtTokenStore());
    }

    /**
     * 配置token的存储方式为jwt方式存储
     *
     * @return
     */
    private TokenStore jwtTokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
        return jwtTokenStore;
    }

    /**
     * Jwt 访问令牌转换器
     * <P>并将其放在ioc容器内</P>
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //resource 验证token（公钥） authorization 产生 token （私钥）
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        String key = null;
        try {
            //获取公钥资源
            ClassPathResource classPathResource = new ClassPathResource("coinExchange.txt");
            //读取公钥用于验证token
            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            //设置公钥的编码为UTF-8
            key = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将取得到的公钥放入访问令牌转换器用于验证token
        tokenConverter.setVerifierKey(key);
        return tokenConverter;
    }
}
