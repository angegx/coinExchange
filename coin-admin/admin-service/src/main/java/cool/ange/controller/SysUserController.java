package cool.ange.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.ange.domain.SysUser;
import cool.ange.model.ResponseResult;
import cool.ange.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ange
 * @package: cool.ange.controller
 * @className: SysUserController
 * @creationTime: 2024-01-10 17:14
 * @Version: v1.0
 * @description: 后台员工管理controller
 */
@Api(tags = "后台员工管理接口", hidden = true)
@Slf4j
@RestController
@RequestMapping("/users")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     *
     * @param page
     * @return
     */
    @GetMapping("/search")
    public ResponseResult<Page<SysUser>> getSysUsersData(Page<SysUser> page) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysUser> sysUserPage = sysUserService.page(page);
        return ResponseResult.success("success", sysUserPage);
    }
}
