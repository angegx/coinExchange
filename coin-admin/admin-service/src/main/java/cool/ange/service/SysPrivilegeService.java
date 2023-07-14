package cool.ange.service;

import cool.ange.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;
import cool.ange.vo.SysMenuVO;

import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.service
 * @className: SysPrivilegeService
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: todo
*/
public interface SysPrivilegeService extends IService<SysPrivilege>{

    /**
     * 根据角色ID获取菜单和权限
     * @param roleId 角色ID
     * @return 菜单和权限
     */
        List<SysMenuVO> getSysMenuAndPrivileges(String roleId);
    }
