package cool.ange.service;

import cool.ange.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.service
 * @className: SysMenuService
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 系统菜单service层
*/
public interface SysMenuService extends IService<SysMenu>{

    /**
     * 根据登录人员的id查询菜单数据
     * @param userId 用户id
     * @return List<SysMenu> 查询到的菜单数据
     */
    List<SysMenu> getMenuDataByUserId(Long userId);
}
