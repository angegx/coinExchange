package cool.ange.controller;

import cool.ange.model.ResponseResult;
import cool.ange.service.SysRolePrivilegeService;
import cool.ange.vo.RolePrivilegeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.controller
 * @className: SysRolePrivilegeController
 * @creationTime: 2024-01-10 10:21
 * @Version: v1.0
 * @description: 系统角色权限配置控制器
 */

@Api(tags = "系统角色权限配置", hidden = true)
@RestController
public class SysRolePrivilegeController {

    private final SysRolePrivilegeService sysRolePrivilegeService;

    public SysRolePrivilegeController(SysRolePrivilegeService sysRolePrivilegeService) {
        this.sysRolePrivilegeService = sysRolePrivilegeService;
    }

    /**
     * 查询角色权限配置列表信息
     *
     * @param roleId 角色的ID
     * @return ResponseResult<RolePrivilegeVO> 角色权限的集合
     */
    @GetMapping("/role_privileges")
    @ApiOperation(value = "查询角色权限配置列表信息", httpMethod = "GET", notes = "查询角色权限配置列表，需要传递查角色的id，返回一个角色权限的集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色的ID", paramType = "query", dataType = "int")
    })
    @PreAuthorize("hasAuthority('sys_role_query')")
    public ResponseResult<List<RolePrivilegeVO>> getSysRolePrivilegesInfo(Long roleId) {
        List<RolePrivilegeVO> sysRolePrivilegesInfo = sysRolePrivilegeService.getSysRolePrivilegesInfo(roleId);
        return ResponseResult.success("数据获取成功！", sysRolePrivilegesInfo);
    }
}
