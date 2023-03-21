package cool.ange.config.swagger;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.List;


/**
 * @author: ange
 * @package: cool.ange.config.swagger
 * @className: SwaggerAutoConfiguration
 * @creationTime: 2023-03-05 10:23
 * @Version: v1.0
 * @description: swagger初始化、安全配置类
 */

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration  {

    private final SwaggerProperties swaggerProperties;

    public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    /**
     * 配置swagger信息
     *
     * @return Docket
     */
    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                //配置是否启用swagger
                .enable(swaggerProperties.getEnable())
                //配置api文档的基本信息，如作者、本版、简介。。。
                .apiInfo(apiInfo())
                .select()
                //配置swagger读取的包路径
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
        // 安全的配置 安全规则
        docket.securitySchemes(securitySchemes())
                // 安全配置的上下问
                .securityContexts(securityContexts());
        return docket;
    }

    /**
     * api 信息的简介
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().contact(
                        //配置api文档的联系信息
                        new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail())
                )
                //配置api文档的标题
                .title(swaggerProperties.getTitle())
                //配置api文档的描述
                .description(swaggerProperties.getDescription())
                //配置api文档的版本信息
                .version(swaggerProperties.getVersion())
                //配置api文档的服务团队
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .build();
    }

    /**
     * 安全的规则配置
     *
     * @return List<SecurityScheme>
     */
    private List<SecurityScheme> securitySchemes() {
        //配置api文档的安全name、key、passAs
        return List.of(new ApiKey("Authorization", "Authorization", "Authorization"));
    }


    /**
     * 安全的上下问
     *
     * @return List
     */
    private List<SecurityContext> securityContexts() {
        return List.of(new SecurityContext(
                List.of(new SecurityReference("Authorization", new AuthorizationScope[]{new AuthorizationScope("global", "accessResource")})),
                PathSelectors.any()
        ));
    }

   /**
     * BeanPostProcessor 在Bean对象在实例化和依赖注入完毕后，在显示调用初始化方法的前后添加我们自己的逻辑。注意是Bean实例化完毕后及依赖注入完成后触发的
     *
     * @return BeanPostProcessor
     */
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            /**
             * 实例化、依赖注入、初始化完毕时执行
             * @param bean 实例
             * @param beanName 实例名称
             * @return Object
             * @throws BeansException BeansException
             */
            @Override
            public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .toList();
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    assert field != null;
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

}

