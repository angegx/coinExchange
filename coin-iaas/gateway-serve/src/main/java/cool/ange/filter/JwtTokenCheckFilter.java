package cool.ange.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author: ange
 * @package: cool.ange.filter
 * @className: JwtTokenCheckFilter
 * @creationTime: 2023-02-19 15:50
 * @Version: v1.0
 * @description: 在网关对所有的请求进行拦截来进行权限认证拦截
 * */

@Component
public class JwtTokenCheckFilter implements GlobalFilter, Ordered {

    private final StringRedisTemplate redisTemplate;

    /**
     * 配置不需要任何权限有可以访问的请求接口，如系统登录接口...
     */
    @Value("${no.require.urls:/admin/login}")
    private Set<String> noRequireUrls;

    public JwtTokenCheckFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 拦截器，在拦截到用户的请求后进行处理
     *
     * @param exchange 服务器网络交换
     * @param chain    网关过滤器链
     * @return Mono
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //判断用户请求是否需要拦截，不需要拦截的请求直接放行
        if (!isRequireToken(exchange)) {
            //不需要token直接放行
            return chain.filter(exchange);
        }
        //获取用户token
        String userToken = getUserToken(exchange);
        //校验token是否为空，为空则返回token不存在
        if (!StringUtils.hasText(userToken)) {
            //没有token 给用户响应一个没有token的错误
            return buildNoAuthorizationResult(exchange);
        }
        //向redis中查询是否存在该请求携带的token，返回boolean值
        Boolean hasKey = redisTemplate.hasKey(userToken);
        //校验查询的token是否为空，且为ture
        if (hasKey != null && hasKey) {
            //token存在并和redis中保持的token值一致，则放行该请求。
            return chain.filter(exchange);
        }
        //通过redis校验失败，返回没有token的错误
        return buildNoAuthorizationResult(exchange);
    }

    /***
     * 判断用户的请求是否需要拦截
     * @param exchange 服务器网络交换
     * @return boolean
     */
    private boolean isRequireToken(ServerWebExchange exchange) {
        //获取请求接口路径
        String path = exchange.getRequest().getURI().getPath();
        //检验是否为不需要认证就可以访问的接口
        if (noRequireUrls.contains(path)) {
            //不需要 返回false
            return false;
        }
        //接口需要认证才可以访问，返回true，由后续程序进行处理
        return Boolean.TRUE;
    }

    /**
     * 给用户响应一个没有token的错误
     *
     * @param exchange 服务器网络交换
     * @return Mono
     */
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        //获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //构建响应体
        response.getHeaders().set("Content-Type", "application/json");
        //设置响应码
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //获取json构建对象
        JSONObject jsonObject = new JSONObject();
        //创建错误类型
        jsonObject.put("error", "NoAuthorization");
        //创建错误学校
        jsonObject.put("errorMsg", "Token is Null or Error token为空或者token错误");
        //构建响应信息
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        //创建并返回一个没有token的错误
        return response.writeWith(Flux.just(wrap));
    }


    /**
     * 从请求头里面获取token
     *
     * @param exchange 服务器网络交换
     * @return String
     */
    private String getUserToken(ServerWebExchange exchange) {
        //从请求中获取token
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        //返回token
        return token == null ? null : token.replace("bearer ", "");
    }


    /**
     * 拦截器的顺序
     *
     * @return int
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
