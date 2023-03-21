package cool.ange.service;

import cool.ange.model.LoginResult;

/**
 * @author: ange
 * @package: cool.ange.service
 * @className: SysLoginService
 * @creationTime: 2023-03-21 11:44
 * @Version: v1.0
 * @description: 后台管理人员登录service层
 */
public interface SysLoginService {

    /**
     * 后台人员登录service层
     *
     * @param username 登录账户
     * @param password 登录密码
     * @return LoginResult 登录的结果
     */
    LoginResult login(String username, String password);
}
