package cool.ange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cool.ange.domain.SysMenu;

import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.mapper
 * @className: SysMenuMapper
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 系统菜单mapper层
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据登录账户id查询对用的菜单数据
     * @param userId 登录账户id
     * @return list 查询到的菜单数据
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}