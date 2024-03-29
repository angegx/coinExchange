package cool.ange.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ange
 * @package: cool.ange.domain
 * @className: SysPrivilege
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: 权限配置
*/
@ApiModel(description="权限配置")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_privilege")
public class SysPrivilege implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 所属菜单Id
     */
    @TableField(value = "menu_id")
    @ApiModelProperty(value="所属菜单Id")
    private Long menuId;

    /**
     * 功能点名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="功能点名称")
    private String name;

    /**
     * 功能描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="功能描述")
    private String description;

    /**
     * url地址
     */
    @TableField(value = "url")
    @ApiModelProperty(value="url地址")
    private String url;

    /**
     * 方法级别控制
     */
    @TableField(value = "`method`")
    @ApiModelProperty(value="方法级别控制")
    private String method;

    /**
     * 创建人
     */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建人")
    private Long createBy;

    /**
     * 修改人
     */
    @TableField(value = "modify_by",fill = FieldFill.UPDATE)
    @ApiModelProperty(value="修改人")
    private Long modifyBy;

    /**
     * 创建时间
     */
    @TableField(value = "created",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;

    /**
     * 标记当前的角色是否有该权限
     */
    @TableField(exist = false)
    @ApiModelProperty(value="当前角色是否拥有这个权限")
    private int own ;

}