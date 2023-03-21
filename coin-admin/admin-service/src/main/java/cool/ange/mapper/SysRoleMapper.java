package cool.ange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cool.ange.domain.SysRole;

/**
 * @author: ange
 * @package: cool.ange.mapper
 * @className: SysRoleMapper
 * @creationTime: 2023-03-21 18:41
 * @Version: v1.0
 * @description: 系统角色mapper层
*/

public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据账户id查询账号的权限码
     * @param userId 账号id
     * @return String 查询到的权限码
     */
    String selectRoleCodeById(Long userId);
}