package cool.ange.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author: ange
 * @package: cool.ange.config
 * @className: AuthorizationServerConfig
 * @creationTime: 2023-02-19 09:38
 * @Version: v1.0
 * @description: 授权服务器配置类
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 认证管理器
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 指定重写的userDateilsServiceImpl
     */
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    public AuthorizationServerConfig(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 访问安全配置项
     * <p>继承SecurityConfigurerAdapter.
     * 也就是一个 Spring Security安全配置提供给AuthorizationServer去配置AuthorizationServer的端点
     * （/oauth/****）的安全访问规则、过滤器Filter</p>
     *
     * @param security 安全
     * @throws Exception exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * 授权客户端配置 配置客户端信息，客户端名称、客户端密钥、有效期等等。
     *
     * @param clients 客户端
     * @throws Exception Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //在内存中配置
        clients.inMemory()
                //第三方客户端的名称
                .withClient("coin_api")
                //第三方客户端的密钥
                .secret(passwordEncoder.encode("coin_secret"))
                //第三方客户端的授权范围
                .scopes("all")
                //配置授权类型
                .authorizedGrantTypes("password", "refresh_token")
                // token的有效期
                .accessTokenValiditySeconds(7 * 24 * 3600)
                //refresh_token的有效期
                .refreshTokenValiditySeconds(30 * 24 * 3600)
                .and()
                //配置服务内部的授权客户端名称
                .withClient("insied_app")
                //配置服务内部的客户端密钥
                .secret(passwordEncoder.encode("inside_secret"))
                //配置授权类型
                .authorizedGrantTypes("client_credentials")
                //授权范围
                .scopes("all")
                //配置服务内部的token有效时间
                .accessTokenValiditySeconds(7 * 24 * 3600)
        ;
        super.configure(clients);
    }

    /**
     * 访问端点配置
     * <p>其实是一个装载类，装载Endpoints所有相关的类配置:</p>
     * <p>（AuthorizationServer、TokenServices、TokenStore、ClientDetailsService、UserDetailsService）</p>
     * @param endpoints 端点
     * @throws Exception exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置身份验证管理器
        endpoints.authenticationManager(authenticationManager)
                //配置用户详情访问
                .userDetailsService(userDetailsService)
                //配置token存储方式为jwt
                .tokenStore(jwtTokenStore())
                //配置jwt访问令牌转换器
                .tokenEnhancer(jwtAccessTokenConverter());
        super.configure(endpoints);
    }

    /**
     * 配置token存储方式为jwt
     * @return TokenStore
     */
    private TokenStore jwtTokenStore() {
        //配置token存储方式
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置jwt访问令牌转换器
     * @return JwtAccessTokenConverter
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        //构建jwt访问令牌转换器对象
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //加载加密密钥
        ClassPathResource keyPathResource = new ClassPathResource("coinExchange.jks");
        //读取密码
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyPathResource, "coinExchange".toCharArray());
        //设置密钥
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("coinExchange", "coinExchange".toCharArray()));
        //返回jwt访问令牌转换器对象
        return jwtAccessTokenConverter;
    }
}
