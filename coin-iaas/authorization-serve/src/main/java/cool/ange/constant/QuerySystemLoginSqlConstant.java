package cool.ange.constant;

/**
 * @author: ange
 * @package: cool.ange.constant
 * @className: QuerySqlConstant
 * @creationTime: 2023-02-26 13:26
 * @Version: v1.0
 * @description: 后台登录sql查询语句常量
 */
public class QuerySystemLoginSqlConstant {

    /**
     * 后台登录查询语句 查询Sys_user表
     */
    public static final String QUERY_ADMIN_SQL = "SELECT `id` ,`username`, `password`, `status` FROM sys_user WHERE username = ? ";

    /**
     * 后台权限码查询语句 判断登录用户是否为admin权限用户
     */
    public static final String QUERY_ROLE_CODE_SQL =
            "SELECT `code` FROM sys_role LEFT JOIN sys_user_role ON sys_role.id = sys_user_role.role_id WHERE sys_user_role.user_id= ?";

    /**
     * 使用后台用户的id 查询用户名称
     */
    public static final String QUERY_ADMIN_USER_WITH_ID = "SELECT `username` FROM sys_user where id = ?";


    /**
     * 后台权限查询语句 若登录人员为admin则拥有所有的后台权限
     */
    public static final String QUERY_ALL_PERMISSIONS = "SELECT `name` FROM sys_privilege";

    /**
     * 后台权限查询语句 后台登录人员为非管理员时，需要通过权限id查询该账户拥有的权限
     */
    public static final String QUERY_PERMISSION_SQL = "SELECT `name` FROM sys_privilege LEFT JOIN sys_role_privilege ON sys_role_privilege.privilege_id = sys_privilege.id LEFT JOIN sys_user_role  ON sys_role_privilege.role_id = sys_user_role.role_id WHERE sys_user_role.user_id = ?";

}
