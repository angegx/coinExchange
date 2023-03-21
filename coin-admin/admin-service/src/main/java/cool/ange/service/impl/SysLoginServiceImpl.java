package cool.ange.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cool.ange.domain.SysMenu;
import cool.ange.extension.ApiErrorCode;
import cool.ange.extension.ApiException;
import cool.ange.feign.JwtToken;
import cool.ange.feign.Oauth2FeignClient;
import cool.ange.model.LoginResult;
import cool.ange.service.SysLoginService;
import cool.ange.service.SysMenuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: SysLoginServiceImpl
 * @creationTime: 2023-03-21 11:47
 * @Version: v1.0
 * @description: 后台管理人员登录实现类
 */
@Service
public class SysLoginServiceImpl implements SysLoginService {

    /**
     * 授权客户端信息
     */
    @Value("${basic.token:Basic Y29pbl9hcGk6Y29pbl9zZWNyZXQ=}")
    private String basicToken;

    private final Oauth2FeignClient oauth2FeignClient;

    private final SysMenuService sysMenuService;

    private final RedisTemplate<String, String> redisTemplate;

    public SysLoginServiceImpl(Oauth2FeignClient oauth2FeignClient, SysMenuService sysMenuService, RedisTemplate<String, String> redisTemplate) {
        this.oauth2FeignClient = oauth2FeignClient;
        this.sysMenuService = sysMenuService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 后台人员登录实现类
     *
     * @param username 登录账户
     * @param password 登录密码
     * @return LoginResult
     */
    @Override
    @SuppressWarnings("all")
    public LoginResult login(String username, String password) {
        //获取token，远程调用authorization-server服务，获取token
        ResponseEntity<JwtToken> jwtTokenResponseEntity = oauth2FeignClient.getJwtToken("password", username, password, "admin_type", basicToken);
        //判断是否调用成功
        if (jwtTokenResponseEntity.getStatusCode() != HttpStatus.OK) {
            //调用失败，抛出操作失败异常
            throw new ApiException(ApiErrorCode.FAILED);
        }
        //获取JwtToken对象
        JwtToken jwtToken = jwtTokenResponseEntity.getBody();
        assert jwtToken != null;
        //获取token
        String token = jwtToken.getAccessToken();
        //解析token得到jwt
        Jwt jwt = JwtHelper.decode(token);
        //得到jwtClaims
        String jwtClaims = jwt.getClaims();
        //得到jwt的json形式
        JSONObject jwtJson = JSON.parseObject(jwtClaims);
        //得到登录用户的id
        Long userId = Long.valueOf(jwtJson.getString("user_name"));
        //调用sysMenusService查询账户对应的菜单数据
        List<SysMenu> menus = sysMenuService.getMenuDataByUserId(userId);
        //解析jwt中的权限信息
        JSONArray jsonJSONArray = jwtJson.getJSONArray("authorities");
        //组装权限数据
        List<SimpleGrantedAuthority> authorities = jsonJSONArray.stream().map(authorityJson -> new SimpleGrantedAuthority(authorityJson.toString()))
                .toList();
        //将token保存到redis中做有效期验证，在网关服务进行校验
        redisTemplate.opsForValue().set(token, token,jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        //我们返回给前端的Token 数据，少一个bearer：所以我们直接要构建一下
        return new LoginResult(jwtToken.getTokenType()+" "+token, menus, authorities);
    }
}
