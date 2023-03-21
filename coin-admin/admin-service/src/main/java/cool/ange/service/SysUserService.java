package cool.ange.service;

import cool.ange.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author: ange
 * @package: cool.ange.service
 * @className: SysUserService
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: todo
*/
public interface SysUserService extends IService<SysUser>{

        /**
         * 根据id查询用户
         * @param userId 用户id
         * @return SysUser
         */
    SysUser getUserInfo(Long userId);
}
