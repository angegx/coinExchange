package cool.ange.constant;

/**
 * @author: ange
 * @package: cool.ange.constant
 * @className: QueryMemberLoginSqlConstant
 * @creationTime: 2023-02-26 15:51
 * @Version: v1.0
 * @description: 会员登录查询语句常量
 */
public class QueryMemberLoginSqlConstant {

    /**
     * 会员查询SQL
     */
    public static final String QUERY_MEMBER_SQL =
            "SELECT `id`,`password`, `status` FROM `user` WHERE mobile = ? or email = ? ";

    /**
     * 使用用户的id 查询用户名称
     */
    public static  final  String QUERY_MEMBER_USER_WITH_ID = "SELECT `mobile` FROM user where id = ?" ;
}
