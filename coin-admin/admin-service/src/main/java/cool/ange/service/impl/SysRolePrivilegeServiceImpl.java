package cool.ange.service.impl;

import cool.ange.domain.SysPrivilege;
import cool.ange.service.SysMenuService;
import cool.ange.service.SysPrivilegeService;
import cool.ange.vo.RolePrivilegeVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.mapper.SysRolePrivilegeMapper;
import cool.ange.domain.SysRolePrivilege;
import cool.ange.service.SysRolePrivilegeService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: SysRolePrivilegeServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 角色权限配置信息业务层接口实现类
 * 前端是以系统菜单为基础，角色权限配置信息是在系统菜单的基础上进行配置的，所以角色权限配置信息的vo对象中需要包含系统菜单的信息。需要查询系统的菜单信息，
 * 在将系统菜单的信息和角色权限配置信息的信息进行合并，返回给前端。
 */
@Service
public class SysRolePrivilegeServiceImpl extends ServiceImpl<SysRolePrivilegeMapper, SysRolePrivilege> implements SysRolePrivilegeService {

    private final SysMenuService sysMenuService;
    private final SysPrivilegeService sysPrivilegeService;

    public SysRolePrivilegeServiceImpl(SysMenuService sysMenuService, SysPrivilegeService sysPrivilegeService) {
        this.sysMenuService = sysMenuService;
        this.sysPrivilegeService = sysPrivilegeService;
    }


    /**
     * 查询角色权限配置列表信息
     *
     * @param roleId 角色的ID
     * @return List<RolePrivilegeVO> 角色权限的集合
     */
    @Override
    public List<RolePrivilegeVO> getSysRolePrivilegesInfo(Long roleId) {
        //查询所有的系统菜单信息,将sysMenu转换为RolePrivilegeVO，因为前端需要的是一个需要组装的对象，所以原来的sysMenu实体类无法满足需求，但是去修改sysMenu并是一个好的选择，所以需要将sysMenu转换为RolePrivilegeVO,
        List<RolePrivilegeVO> sysMenuList = sysMenuService.list().stream().map(sysMenu -> {
            //将sysMenu转换为RolePrivilegeVO，创建一个RolePrivilegeVO对象
            RolePrivilegeVO rolePrivilegeVO = new RolePrivilegeVO();
            //将sysMenu的id属性值赋值给rolePrivilegeVO的id属性
            rolePrivilegeVO.setId(sysMenu.getId());
            //将sysMenu的parentId属性值赋值给rolePrivilegeVO的parentId属性
            rolePrivilegeVO.setParentId(sysMenu.getParentId());
            //将sysMenu的parentKey属性值赋值给rolePrivilegeVO的parentKey属性
            rolePrivilegeVO.setParentKey(sysMenu.getParentKey());
            //将sysMenu的type属性值赋值给rolePrivilegeVO的type属性
            rolePrivilegeVO.setType(sysMenu.getType());
            //将sysMenu的name属性值赋值给rolePrivilegeVO的name属性
            rolePrivilegeVO.setName(sysMenu.getName());
            //将sysMenu的url属性值赋值给rolePrivilegeVO的url属性
            rolePrivilegeVO.setTargetUrl(sysMenu.getTargetUrl());
            //将sysMenu的sort属性值赋值给rolePrivilegeVO的sort属性
            rolePrivilegeVO.setSort(sysMenu.getSort());
            return rolePrivilegeVO;
        }).toList();
        //校验系统菜单信息是否为空
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return Collections.emptyList();
        }
        //根据系统菜单信息，获取一级菜单信息
        List<RolePrivilegeVO> firstLevelMenu = sysMenuList.stream().filter(sysMenu -> sysMenu.getParentId() == null).toList();
        //校验一级菜单信息是否为空
        if (CollectionUtils.isEmpty(firstLevelMenu)) {
            return Collections.emptyList();
        }
        List<RolePrivilegeVO> rolePrivilegeVOList = new ArrayList<>();
        for (RolePrivilegeVO sysMenu : firstLevelMenu) {
            rolePrivilegeVOList.addAll(getChildrenMenus(sysMenu.getId(), roleId, sysMenuList));
        }
        return rolePrivilegeVOList;
    }


    private List<RolePrivilegeVO> getChildrenMenus(Long firstLevelMenuId, Long roleId, List<RolePrivilegeVO> menuDataSource) {
        List<RolePrivilegeVO> rolePrivilegeVO = new ArrayList<>();
        for (RolePrivilegeVO source : menuDataSource) {
            if (source.getParentId() != null) {
                if (source.getParentId().equals(firstLevelMenuId)) {
                    rolePrivilegeVO.add(source);
                    source.setChildren(getChildrenMenus(source.getId(), roleId, menuDataSource));
                    List<SysPrivilege> sysPrivilegeList = sysPrivilegeService.getAllPrivilegeDataByRoleIdId(source.getId(), roleId);
                    source.setPrivileges(sysPrivilegeList);
                }
            }
        }
        return rolePrivilegeVO;
    }

}
