package cool.ange.controller;

import cool.ange.model.LoginResult;
import cool.ange.model.ResponseResult;
import cool.ange.service.SysLoginService;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
     *
     * @param username 登录账户
     * @param password 登录密码
     * @return LoginResult 登录的结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "后台登录接口", httpMethod = "POST", notes = "后台管理人员登录系统接口，需要传递登录账户、密码参数。返回一个LoginResult实体类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录账户", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "username", value = "登录密码", required = true, paramType = "query", dataType = "String")
    })
    public ResponseResult<LoginResult> login(@ApiParam(name = "username", value = "登录账户", required = true) String username,
                                @ApiParam(name = "password", value = "登录密码", required = true) String password) {
        return ResponseResult.success("success",sysLoginService.login(username, password));
    }

    /**
     * 刷新token，功能展示不确定是否需要，先写一个假接口
     * @return ResponseResult<String>
     */
    @GetMapping("/refreshToken")
    public ResponseResult<String> GetRefreshToken(){
        return ResponseResult.success("success","功能暂时不确定是否需要，先写一个假接口");
    }
}
