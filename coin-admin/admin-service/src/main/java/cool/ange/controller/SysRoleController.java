package cool.ange.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cool.ange.domain.SysRole;
import cool.ange.model.ResponseResult;
import cool.ange.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.utils.base.annotation.NotNull;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @author: ange
 * @package: cool.ange.controller
 * @className: SysRoleController
 * @creationTime: 2023-04-06 11:09
 * @Version: v1.0
 * @description: 系统角色控制器
 */

@RestController
@Api(tags = "系统角色", hidden = true)
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 条件分页查询系统角色数据，可根据角色名称进行条件查询，如果不传递角色名称，则查询所有的系统角色数据。
     * @param page 分页对象
     * @param name 角色名称
     * @return ResponseResult 返回一个Page<SysRole>实体类
     */
    @GetMapping("/roles")
    @ApiOperation(value = "条件分页查询系统角色数据", httpMethod = "GET", notes = "分页查询系统角色数据，需要传递当前页，每页显示的行数，返回一个Page<SysRole>实体类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页显示的行数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "每页显示的行数", paramType = "query", dataType = "string")
    })
    @PreAuthorize("hasAuthority('sys_role_query')")
    public ResponseResult<Page<SysRole>> getSysRolesDataByPage(@ApiIgnore Page<SysRole> page, String name) {
        return ResponseResult.success("success", sysRoleService.getSysRolesDataByPage(page, name));
    }

    /**
     * 根据id查询系统角色数据,返回对应的系统角色信息
     *
     * @param id 系统角色id
     * @return ResponseResult 返回一个SysRole实体类
     */
    @GetMapping("/roles/{id}")
    @ApiOperation(value = "根据id查询系统角色数据", httpMethod = "GET", notes = "根据id查询系统角色数据，需要传递id，返回一个SysRole实体类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统角色id", paramType = "path", dataType = "string")
    })
    @PreAuthorize("hasAuthority('sys_role_query')")
    public ResponseResult<SysRole> getSysRoleDataById(@PathVariable("id") @NotNull String id) {
        // 根据id查询系统角色数据
        SysRole sysRole = sysRoleService.getById(id);
        // 判断是否查询到数据,如果查询到数据则返回数据，否则返回null
        return sysRole != null ? ResponseResult.success("success", sysRole) : ResponseResult.fail("无效的角色的id，无法查询到数据", null);
    }

    /**
     * 添加系统角色数据
     *
     * @param sysRole 系统角色实体类
     * @return ResponseResult 返回一个String类型的字符串
     */
    @PostMapping("/roles")
    @ApiOperation(value = "添加系统角色数据", httpMethod = "POST", notes = "添加系统角色数据，需要传递SysRole实体类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "系统角色实体类", paramType = "body", dataType = "SysRole")
    })
    @PreAuthorize("hasAuthority('sys_role_create')")
    public ResponseResult<String> addSysRoleData(@RequestBody @Validated SysRole sysRole){
        return sysRoleService.save(sysRole) ? ResponseResult.success("success","新增成功") : ResponseResult.fail("error","新增失败");
    }

    /**
     * 编辑系统角色数据
     *
     * @param sysRole 系统角色实体类
     * @return ResponseResult 返回一个String类型的字符串
     */
    @PatchMapping("/roles")
    @ApiOperation(value = "编辑系统角色数据", httpMethod = "PATCH", notes = "编辑系统角色数据，需要传递SysRole实体类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "系统角色实体类", paramType = "body", dataType = "SysRole")
    })
    @PreAuthorize("hasAuthority('sys_role_update')")
    public ResponseResult<String> updateSysRoleData(@RequestBody SysRole sysRole){
        return sysRoleService.updateById(sysRole) ? ResponseResult.success("success","编辑成功") : ResponseResult.fail("error","编辑失败");
    }

    /**
     * 删除系统角色数据，根据系统角色id进行删除。一次只能删除一个系统角色数据，不运行批量删除。
     *
     * @param id 系统角色id
     * @return ResponseResult 返回一个String类型的字符串
     */
    @DeleteMapping("/roles/{id}")
    @ApiOperation(value = "删除系统角色数据", httpMethod = "DELETE", notes = "删除系统角色数据，需要传递系统角色id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统角色id", paramType = "path", dataType = "string")
    })
    @PreAuthorize("hasAuthority('sys_role_delete')")
    public ResponseResult<String> deleteSysRoleData(@PathVariable @NotNull String id){
        return sysRoleService.removeById(id) ? ResponseResult.success("success","删除成功") : ResponseResult.fail("error","删除失败");
    }

}
