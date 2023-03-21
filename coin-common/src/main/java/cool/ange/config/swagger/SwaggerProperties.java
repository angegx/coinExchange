package cool.ange.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: ange
 * @package: cool.ange.config.swagger
 * @className: SwaggerProperties
 * @creationTime: 2023-03-05 10:25
 * @Version: v1.0
 * @description: swagger配置属性类
 */
@Data
@ConfigurationProperties(prefix = "swagger2")
public class SwaggerProperties {

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable;

    /**
     * 包扫描的路径
     */
    private  String basePackage ;

    /**
     * 联系人的名称
     */
    private String name ;

    /**
     * 联系人的主页
     */
    private String url ;

    /**
     * 联系人的邮箱
     */
    private String email ;

    /**
     * API的标题
     */
    private String  title  ;

    /**
     * API的描述
     */
    private String description ;

    /**
     * API的版本号
     */
    private String version ;

    /**
     * API的服务团队
     */
    private String termsOfServiceUrl ;


}
