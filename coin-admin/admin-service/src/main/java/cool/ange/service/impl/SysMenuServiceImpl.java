package cool.ange.service.impl;

import cool.ange.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.domain.SysMenu;
import cool.ange.mapper.SysMenuMapper;
import cool.ange.service.SysMenuService;

/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: SysMenuServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 系统菜单实现类
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleService sysRoleService;

    private final SysMenuMapper sysMenuMapper;

    public SysMenuServiceImpl(SysRoleService sysRoleService, SysMenuMapper sysMenuMapper) {
        this.sysRoleService = sysRoleService;
        this.sysMenuMapper = sysMenuMapper;
    }


    /**
     * 根据登录人员的id查询菜单数据
     *
     * @param userId 用户id
     * @return List<SysMenu> 查询到的菜单数据
     */
    @Override
    public List<SysMenu> getMenuDataByUserId(Long userId) {
        //判断账户是否为超级管理员
        if (sysRoleService.isSuperAdmin(userId)){
            //是超级管理员则应有全部的菜单数据
            return list();
        }
        //不是超级管理员则按照普通的后台管理人员查询对应的菜单数据
        return sysMenuMapper.selectMenusByUserId(userId);
    }
}
