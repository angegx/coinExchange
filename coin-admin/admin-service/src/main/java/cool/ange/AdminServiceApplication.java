package cool.ange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: ange
 * @package: cool.ange
 * @className: AdminServiceApplication
 * @creationTime: 2023-03-20 12:11
 * @Version: v1.0
 * @description: 系统后台服务管理启动类
 */

@SpringBootApplication
@EnableDiscoveryClient
public class AdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class,args);
    }
}
