package cool.ange.service;

import cool.ange.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author: ange
 * @package: cool.ange.service
 * @className: SysRoleService
 * @creationTime: 2023-03-21 18:41
 * @Version: v1.0
 * @description: 系统角色service层
*/
public interface SysRoleService extends IService<SysRole>{

        /**
         * 判断后台登录账户是否为超级管理员
         * @param userId 登录账号id
         * @return boolean 判断是否为超级管理员账号
         */
    boolean isSuperAdmin(Long userId);
}
