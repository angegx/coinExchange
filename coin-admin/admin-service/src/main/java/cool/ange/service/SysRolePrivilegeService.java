package cool.ange.service;

import cool.ange.domain.SysRolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;
import cool.ange.vo.RolePrivilegeVO;

import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.service
 * @className: SysRolePrivilegeService
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 角色权限配置信息业务层接口
 */
public interface SysRolePrivilegeService extends IService<SysRolePrivilege> {


    /**
     * 查询角色权限配置列表信息
     *
     * @param roleId 角色的ID
     * @return List<RolePrivilegeVO> 角色权限的集合
     */
    List<RolePrivilegeVO> getSysRolePrivilegesInfo(Long roleId);
}
