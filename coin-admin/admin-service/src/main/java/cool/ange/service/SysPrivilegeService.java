package cool.ange.service;

import cool.ange.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.service
 * @className: SysPrivilegeService
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 系统权限服务service接口
 */
public interface SysPrivilegeService extends IService<SysPrivilege> {

    /**
     * 根据角色id，菜单id 查询该角色所对应的权限
     *
     * @param roleId 角色的id
     * @param menuId 菜单的id
     * @return List<SysPrivilege> 角色对用的权限集合
     */
    List<SysPrivilege> getAllPrivilegeDataByRoleIdId(Long menuId, Long roleId);
}
