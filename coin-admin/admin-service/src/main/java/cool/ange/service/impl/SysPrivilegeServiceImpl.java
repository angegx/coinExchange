package cool.ange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.mapper.SysPrivilegeMapper;
import cool.ange.domain.SysPrivilege;
import cool.ange.service.SysPrivilegeService;
import org.springframework.util.CollectionUtils;

/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: SysPrivilegeServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 系统权限服务实现类
 */
@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService {

    private final SysPrivilegeMapper sysPrivilegeMapper;

    public SysPrivilegeServiceImpl(SysPrivilegeMapper sysPrivilegeMapper) {
        this.sysPrivilegeMapper = sysPrivilegeMapper;
    }


    /**
     * 根据角色id，菜单id 查询该角色所对应的权限
     *
     * @param menuId 菜单的id
     * @param roleId 角色的id
     * @return List<SysPrivilege> 角色对用的权限集合
     */
    @Override
    public List<SysPrivilege> getAllPrivilegeDataByRoleIdId(Long menuId, Long roleId) {
        List<SysPrivilege> sysPrivilegeList = list(new LambdaQueryWrapper<SysPrivilege>().eq(SysPrivilege::getMenuId, menuId));
        if (CollectionUtils.isEmpty(sysPrivilegeList)){
            return Collections.emptyList();
        }
        //当前的角色是否拥有权限，需要做筛选，将当前角色拥有的权限保留， 其他的权限剔除
        for (SysPrivilege sysPrivilege : sysPrivilegeList) {
            Set<Long> currentRoleSysPrivilegeIds= sysPrivilegeMapper.getPrivilegeByRoleId(roleId);
            System.out.println(currentRoleSysPrivilegeIds);
            if (currentRoleSysPrivilegeIds.contains(sysPrivilege.getId())){
               sysPrivilege.setOwn(1);
           }
        }
        return sysPrivilegeList;
    }
}
