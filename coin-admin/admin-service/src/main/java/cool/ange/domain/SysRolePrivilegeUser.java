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
 * @className: SysRolePrivilegeUser
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 用户权限配置
*/
@ApiModel(description="用户权限配置")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_privilege_user")
public class SysRolePrivilegeUser implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 角色Id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value="角色Id")
    private Long roleId;

    /**
     * 用户Id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户Id")
    private Long userId;

    /**
     * 权限Id
     */
    @TableField(value = "privilege_id")
    @ApiModelProperty(value="权限Id")
    private Long privilegeId;
}