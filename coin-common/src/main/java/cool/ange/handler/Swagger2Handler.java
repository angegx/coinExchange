package cool.ange.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.handler
 * @className: Swagger2Handler
 * @creationTime: 2023-03-20 17:06
 * @Version: v1.0
 * @description: 配置swagger-ui需要依赖的一些接口
 */

@RestController
public class Swagger2Handler {

    private final SwaggerResourcesProvider swaggerResourcesProvider;

    public Swagger2Handler(SwaggerResourcesProvider swaggerResourcesProvider) {
        this.swaggerResourcesProvider = swaggerResourcesProvider;
    }

    @GetMapping("/")
    public Mono<ResponseEntity<List<SwaggerResource>>> swaggerResourcesN(){
        return Mono.just(new ResponseEntity<>(swaggerResourcesProvider.get(), HttpStatus.OK));
    }

    @GetMapping("/csrf")
    public Mono<ResponseEntity<List<SwaggerResource>>> swaggerResourcesCsrf(){
        return Mono.just(new ResponseEntity<>(swaggerResourcesProvider.get(), HttpStatus.OK));
    }
}
