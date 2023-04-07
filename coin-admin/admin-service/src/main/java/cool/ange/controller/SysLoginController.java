package cool.ange.controller;

import cool.ange.model.LoginResult;
import cool.ange.model.ResponseResult;
import cool.ange.service.SysLoginService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: ange
 * @package: cool.ange.controller
 * @className: SysLoginController
 * @creationTime: 2023-03-21 11:18
 * @Version: v1.0
 * @description: 后台人员登录controller类
 */
@RestController
@Api(tags = "后台人员登录", hidden = true)
public class SysLoginController {

    private final SysLoginService sysLoginService;

    public SysLoginController(SysLoginService sysLoginService) {
        this.sysLoginService = sysLoginService;
    }

    /**
     * 后台人员登录接口
     * @return LoginResult 登录的结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "后台登录接口", httpMethod = "POST", notes = "后台管理人员登录系统接口，需要传递登录账户、密码参数。返回一个LoginResult实体类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录账户", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, paramType = "query", dataType = "String")
    })
    public ResponseResult<LoginResult> login(@RequestBody Map<String, String> params) {
        return ResponseResult.success("success", sysLoginService.login(params.get("username"), params.get("password")));
    }

    /**
     * 刷新token，功能展示不确定是否需要，先写一个假接口
     *
     * @return ResponseResult<String>
     */
    @GetMapping("/refreshToken")
    public ResponseResult<String> getRefreshToken() {
        return ResponseResult.success("success", "功能暂时不确定是否需要，先写一个假接口");
    }
}
