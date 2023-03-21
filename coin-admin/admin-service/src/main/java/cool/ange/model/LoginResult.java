package cool.ange.model;

import cool.ange.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.model
 * @className: LoginResult
 * @creationTime: 2023-03-21 10:08
 * @Version: v1.0
 * @description: 登录成功返回结果类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录成功返回结果类",description = "当登录成功后，将以此类为返回结果返回token，账号拥有的系统菜单和权限。")
public class LoginResult {

    /**
     * token，登录成功后返回的token。来自有authorization-server服务中生成的jwt
     */
    @ApiModelProperty(value = "token,登录成功后返回的token。",dataType = "String",required = true)
    private String token;

    /**
     * 登录账号所拥有的菜单数据
     */
    @ApiModelProperty(value = "登录账号所拥有的菜单数据。",dataType ="String" ,required = true,position = 1)
    private List<SysMenu> menus;

    /**
     * 登录账户所拥有的权限数据
     */
    @ApiModelProperty(value = "登录账户所拥有的权限数据。",dataType ="String" ,required = true,position = 2)
    private List<SimpleGrantedAuthority> authorities;

}
