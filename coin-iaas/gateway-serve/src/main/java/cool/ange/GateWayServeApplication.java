package cool.ange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: ange
 * @package: cool.ange
 * @className: GateWayServeApplication
 * @creationTime: 2023-02-12 17:36
 * @Version: v1.0
 * @description: 网关中心启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayServeApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayServeApplication.class, args);
    }
}
