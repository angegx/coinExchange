package cool.ange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cool.ange.domain.SysPrivilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author: ange
 * @package: cool.ange.mapper
 * @className: SysPrivilegeMapper
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 系统权限配置mapper层
*/

@Mapper
public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {

    /**
     * 使用角色的id查询权限的id
     * @param roleId 角色的id
     * @return Set<Long> 权限的id
     */
    Set<Long> getPrivilegeByRoleId(@Param("roleId") Long roleId);
}