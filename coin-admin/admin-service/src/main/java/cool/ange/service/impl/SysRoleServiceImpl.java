package cool.ange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.mapper.SysRoleMapper;
import cool.ange.domain.SysRole;
import cool.ange.service.SysRoleService;
import org.springframework.util.StringUtils;
/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: SysRoleServiceImpl
 * @creationTime: 2023-03-21 18:41
 * @Version: v1.0
 * @description: 系统角色实现类
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final static String ROLE_ADMIN = "ROLE_ADMIN";

    private final SysRoleMapper sysRoleMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    /**
     * 判断后台登录账户是否为超级管理员
     *
     * @param userId 登录账号id
     * @return boolean 判断是否为超级管理员账号
     */
    @Override
    public boolean isSuperAdmin(Long userId) {
        // 根据用户id查询角色编码
        String roleCode = sysRoleMapper.selectRoleCodeById(userId);
        // 判断角色编码是否为超级管理员
        return StringUtils.hasText(roleCode) && roleCode.equals(ROLE_ADMIN);
    }

    /**
     * 分页查询系统角色数据
     *
     * @param page 分页对象
     * @param name 角色名称
     * @return Page<SysRole> 返回一个Page<SysRole>实体类
     */
    @Override
    public Page<SysRole> getSysRolesDataByPage(Page<SysRole> page, String name) {
        // 按更新时间排序
        page.addOrder(OrderItem.desc("last_update_time"));
        // 按角色名称模糊查询
        return page(page, new LambdaQueryWrapper<SysRole>().like(StringUtils.hasText(name), SysRole::getName, name));
    }
}
