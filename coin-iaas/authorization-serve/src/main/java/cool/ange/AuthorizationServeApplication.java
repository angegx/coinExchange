package cool.ange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: ange
 * @package: cool.ange
 * @className: AuthorizationServeApplication
 * @creationTime: 2023-02-12 19:28
 * @Version: v1.0
 * @description: 认证授权中心启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServeApplication.class, args);
    }
}
