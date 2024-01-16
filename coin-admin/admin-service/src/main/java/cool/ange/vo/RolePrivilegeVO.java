package cool.ange.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import cool.ange.domain.SysPrivilege;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: ange
 * @package: cool.ange.vo
 * @className: RolePrivilegeVO
 * @creationTime: 2024-01-10 10:46
 * @Version: v1.0
 * @description: 1.角色权限配置信息vo表示层对象, 用于传递前端传递的角色权限配置信息，以及返回前端的角色权限配置信息，不需要持久化，不需要mapper。
 * 2.在前端页面展示是以系统菜单为基础，角色权限配置信息是在系统菜单的基础上进行配置的，所以角色权限配置信息的vo对象中需要包含系统菜单的信息。
 * 3.需要查询系统菜单的信息，在将系统菜单的信息和角色权限配置信息的信息进行合并，返回给前端。
 */

@ApiModel(description = "角色权限配置信息vo表示层对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePrivilegeVO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 上级菜单ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "上级菜单ID")
    private Long parentId;

    /**
     * 上级菜单唯一KEY值
     */
    @TableField(value = "parent_key")
    @ApiModelProperty(value = "上级菜单唯一KEY值")
    private String parentKey;

    /**
     * 类型 1-分类 2-节点
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value = "类型 1-分类 2-节点")
    private Byte type;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value = "描述")
    private String desc;

    /**
     * 目标地址
     */
    @TableField(value = "target_url")
    @ApiModelProperty(value = "目标地址")
    private String targetUrl;

    /**
     * 排序索引
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序索引")
    private Integer sort;

    /**
     * 一个菜单对应多个权限
     */
    @TableField(exist = false)
    @ApiModelProperty("该菜单下的所有的权限")
    private List<SysPrivilege> privileges;

    /**
     * 一个菜单对应多个子菜单
     */
    @TableField(exist = false)
    @ApiModelProperty("该菜单的子菜单")
    private List<RolePrivilegeVO> children;

    /**
     * 主要是和前台VUE显示相关
     */
    @TableField(exist = false)
    @ApiModelProperty("该菜单的唯一Key值")
    private String menuKey;

    /**
     * 获取菜单的唯一Key凭证
     *
     * @return String 菜单的唯一Key凭证
     */
    public String getMenuKey() {
        if (!StringUtils.hasText(parentKey)) {
            return parentKey + "." + id;
        } else {
            return id.toString();
        }
    }

}
