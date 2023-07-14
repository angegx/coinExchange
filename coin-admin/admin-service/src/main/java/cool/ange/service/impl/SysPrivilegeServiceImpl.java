package cool.ange.service.impl;

import cool.ange.service.SysMenuService;
import cool.ange.vo.SysMenuVO;
import org.springframework.stereotype.Service;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.mapper.SysPrivilegeMapper;
import cool.ange.domain.SysPrivilege;
import cool.ange.service.SysPrivilegeService;

/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: SysPrivilegeServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: todo
*/
@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService{

    private final SysMenuService sysMenuService;

    public SysPrivilegeServiceImpl(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }
    /**
     * 根据角色ID获取菜单和权限
     *
     * @param roleId 角色ID
     * @return 菜单和权限
     */
    @Override
    public List<SysMenuVO> getSysMenuAndPrivileges(String roleId) {
        return null;
    }
}
