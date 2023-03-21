package cool.ange.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: ange
 * @package: cool.ange.domain
 * @className: SysRolePrivilege
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 角色权限配置
*/
@ApiModel(description="角色权限配置")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_privilege")
public class SysRolePrivilege implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 系统角色id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value="系统角色id")
    private Long roleId;

    /**
     * 权限id
     */
    @TableField(value = "privilege_id")
    @ApiModelProperty(value="权限id")
    private Long privilegeId;
}