package cool.ange.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: ange
 * @package: cool.ange.feign
 * @className: Oauth2FeignClient
 * @creationTime: 2023-03-21 11:54
 * @Version: v1.0
 * @description: 远程调用authorization-server服务获取token
 */
@FeignClient(value = "coin-authorization-serve")
public interface Oauth2FeignClient {

    /**
     * 远程调用coin-authorization-server 获取token
     *
     * @param grantType  授权类型
     * @param username   用户名
     * @param password   用户的密码
     * @param loginType  登录的类型
     * @param basicToken Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ= 由第三方客户端信息加密出现的值
     * @return ResponseEntity 获取token的返回结果
     */
    @PostMapping("/oauth/token")
    ResponseEntity<JwtToken> getJwtToken(@RequestParam("grant_type") String grantType,
                                         @RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam("login_type") String loginType,
                                         @RequestHeader("Authorization") String basicToken
    );
}
