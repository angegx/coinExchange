package cool.ange.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.mapper.SysUserMapper;
import cool.ange.domain.SysUser;
import cool.ange.service.SysUserService;

/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: SysUserServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: todo
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 根据id查询用户
     *
     * @param userId 用户id
     * @return SysUser
     */
    @Override
    @Cached(name = "cool.ange.service.impl.SysUserServiceImpl:", key = "#userId", expire = 3600, cacheType = CacheType.REMOTE)
    public SysUser getUserInfo(Long userId) {
        return sysUserMapper.selectById(userId);
    }
}
