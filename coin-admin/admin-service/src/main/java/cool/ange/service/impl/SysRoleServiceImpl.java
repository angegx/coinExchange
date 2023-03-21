package cool.ange.service.impl;

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
        String roleCode = sysRoleMapper.selectRoleCodeById(userId);
        return StringUtils.hasText(roleCode) && roleCode.equals(ROLE_ADMIN);
    }
}
