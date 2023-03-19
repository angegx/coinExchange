package cool.ange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author: ange
 * @package: cool.ange.config
 * @className: WebSecurityConfig
 * @creationTime: 2023-02-19 10:50
 * @Version: v1.0
 * @description: security安全配置
 */

@Configuration
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    /**
     * 配置跨域问题
     * @param http HttpSecurity
     * @throws Exception exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启跨域许可
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
    }

    /**
     * 配置AuthenticationManager 身份验证管理器
     * @return AuthenticationManager
     * @throws Exception exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 配置密码编码器
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
