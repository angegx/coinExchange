package cool.ange.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.ange.domain.SysPrivilege;
import cool.ange.model.ResponseResult;
import cool.ange.service.SysPrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author: ange
 * @package: cool.ange.controller
 * @className: SysRolePrivilegeController
 * @creationTime: 2023-03-22 19:45
 * @Version: v1.0
 * @description: 权限配置controller层
 */

@RestController
@Api(tags = "权限配置", hidden = true)
@Slf4j
public class SysPrivilegeController {

    private final SysPrivilegeService sysPrivilegeService;

    public SysPrivilegeController(SysPrivilegeService sysPrivilegeService) {
        this.sysPrivilegeService = sysPrivilegeService;
    }

    /**
     * 分页查询系统权限数据
     *
     * @param page 分页对象
     * @return ResponseResult 返回一个Page<SysPrivilege>实体类
     */
    @GetMapping("/privileges")
    @ApiOperation(value = "分页查询系统权限数据", httpMethod = "POST", notes = "分页查询系统权限数据，需要传递当前页，每页显示的行数，返回一个Page<SysPrivilege>实体类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的行数", required = true, paramType = "query", dataType = "int")
    })
    @PreAuthorize("hasAuthority('sys_privilege_query')")
    public ResponseResult<Page<SysPrivilege>> getSysPrivilegesData(@ApiIgnore Page<SysPrivilege> page) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPrivilege> sysPrivilegePage = sysPrivilegeService.page(page);
        return ResponseResult.success("success", sysPrivilegePage);
    }

    /**
     * 添加系统权限数据
     *
     * @param sysPrivilege 系统权限实体类
     * @return ResponseResult 返回一个String类型的success
     */
    @PostMapping("/privileges")
    @ApiOperation(value = "添加系统权限数据", httpMethod = "POST", notes = "添加系统权限数据，需要传递SysPrivilege实体类，返回一个String类型的success")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "系统权限实体类", required = true, paramType = "body", dataType = "SysPrivilege")
    })
    @PreAuthorize("hasAuthority('sys_privilege_create')")
    public ResponseResult<String> addSysPrivilegesData(@RequestBody @Validated SysPrivilege sysPrivilege) {
        return sysPrivilegeService.save(sysPrivilege) ? ResponseResult.success("success", "新增成功") : ResponseResult.fail("fail", "新增失败");
    }

    /**
     * 修改系统权限数据
     *
     * @param sysPrivilege 系统权限实体类
     * @return ResponseResult 返回一个String类型的success
     */
    @PatchMapping("/privileges")
    @ApiOperation(value = "修改系统权限数据", httpMethod = "POST", notes = "修改系统权限数据，需要传递SysPrivilege实体类，返回一个String类型的success")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege", value = "系统权限实体类", required = true, paramType = "body", dataType = "SysPrivilege")
    })
    @PreAuthorize("hasAuthority('sys_privilege_update')")
    public ResponseResult<String> updateSysPrivilegesData(@RequestBody @Validated SysPrivilege sysPrivilege) {
        return sysPrivilegeService.updateById(sysPrivilege) ? ResponseResult.success("success", "修改成功") : ResponseResult.fail("fail", "修改失败");
    }

    /**
     * 删除系统权限数据，根据id一次删除一条数据。不允许批量删除。
     *
     * @param id 系统权限id
     * @return ResponseResult 返回一个String类型的success
     */
    @DeleteMapping("/privileges/{id}")
    @ApiOperation(value = "删除系统权限数据", httpMethod = "POST", notes = "删除系统权限数据，需要传递id，返回一个String类型的success")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统权限id", required = true, paramType = "path", dataType = "string")
    })
    @PreAuthorize("hasAuthority('sys_privilege_delete')")
    public ResponseResult<String> deleteSysPrivilegesData(@PathVariable("id") String id) {
        return sysPrivilegeService.removeById(id) ? ResponseResult.success("success", "删除成功") : ResponseResult.fail("fail", "删除失败");
    }
}
