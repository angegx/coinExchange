package cool.ange.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author: ange
 * @package: cool.ange.controller
 * @className: UserInfoController
 * @creationTime: 2023-02-19 12:56
 * @Version: v1.0
 * @description: 读取当前登录的用户对象
 */
@RestController
public class UserInfoController {

    /**
     * 获取当前登录用户对象
     * @param principal security当前登录用户对象
     * @return java.security.Principal
     */
    @GetMapping("/user/info")
     Principal userInfo(Principal principal){
        return principal;
    }
}
