package cool.ange.service.impl;

import cool.ange.constant.LoginTypeConstant;
import cool.ange.constant.QueryMemberLoginSqlConstant;
import cool.ange.constant.QuerySystemLoginSqlConstant;
import cool.ange.constant.SystemRoleConstant;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: UserDetailsServiceImpl
 * @creationTime: 2023-02-19 19:41
 * @Version: v1.0
 * @description: 授权客户端权限查询实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    public UserDetailsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param username 用户名
     * @return org.springframework.security.core.userDetails.UserDetails;
     * @throws UsernameNotFoundException 用户名未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取请求信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        //得到用户的登录类型，是管理员登录还是会员登录
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        //判断登录类型是否为允许的
        if (!StringUtils.hasText(loginType)) {
            //登录类型不允许抛出异常
            throw new AuthenticationServiceException("登录类型不能为null");
        }
        //获取grant_type认证类型
        String grantType = requestAttributes.getRequest().getParameter("grant_type");
        UserDetails userDetails;
        try {
            //判断grant_type类型是否为refresh_token
            if (LoginTypeConstant.REFRESH_TYPE.equalsIgnoreCase(grantType)) {
                //若授权类型为refresh_token则需要对用户名进行矫正（在这之前我们使用了用户的id作为用户的用户名，所以我们要用用户id查询到用户的用户名）
                username = adjustLoginUserName(username, loginType);
            }
            //判断是后台人员登录还是会员登录
            userDetails = switch (loginType) {
                //后台人员登录
                case LoginTypeConstant.ADMIN_TYPE -> loadSystemUserByUsername(username);
                //会员登录
                case LoginTypeConstant.MEMBER_TYPE -> loadMemberUserByUsername(username);
                //两者的都不是，抛出“暂不支持的登录方式”异常
                default -> throw new AuthenticationServiceException("暂不支持的登录方式" + loginType);
            };
        } catch (IncorrectResultSizeDataAccessException e) {
            //根据用户名查询不到权限信息，抛出用户名不存在异常
            throw new UsernameNotFoundException("用户名" + username + "不存在！");
        }
        //返回userDetails对象
        return userDetails;
    }

    /**
     * 对登录名进行矫正（纠正在refresh场景下登录名不存在问题）
     *
     * @param username  登录的用户名
     * @param loginType 登录类型
     * @return String
     */
    private String adjustLoginUserName(String username, String loginType) {
        //判断是否为后台人员登录
        if (LoginTypeConstant.ADMIN_TYPE.equals(loginType)) {
            //返回根据id查询的用户名
            return jdbcTemplate.queryForObject(QuerySystemLoginSqlConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        }
        //判断是否为会员登录
        if (LoginTypeConstant.MEMBER_TYPE.equals(loginType)) {
            //返回根据id查询的用户名
            return jdbcTemplate.queryForObject(QueryMemberLoginSqlConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        }
        return username;
    }

    /**
     * 登录类型为管理员类型的类型
     *
     * @param username 登录的管理员用户名
     * @return UserDetails
     */
    private UserDetails loadSystemUserByUsername(String username) {
        //查询后台人员的权限信息
        return jdbcTemplate.queryForObject(QuerySystemLoginSqlConstant.QUERY_ADMIN_SQL, (rs, rowNum) -> {
            //判断用户名是否有效
            if (rs.wasNull()) {
                //构建用户名无效异常
                throw new UsernameNotFoundException("用户名" + username + "不存在！");
            }
            //获取查询到的用户id
            long loginSystemUserId = rs.getLong("id");
            //获取用户密码
            String password = rs.getString("password");
            //获取用户是否处于使用中
            int status = rs.getInt("status");
            //构建用户类，并将用户id作为用户名，同时校验用户是否处于使用中
            return new User(String.valueOf(loginSystemUserId), password, status == 1, true, true, true, getSystemUserPermissions(loginSystemUserId));
        }, username);
    }

    /**
     * 通过系统用户id查询用户权限数据
     *
     * @param loginSystemUserId 用户id
     * @return Collection
     */
    private Collection<? extends GrantedAuthority> getSystemUserPermissions(long loginSystemUserId) {
        //查询后台登录人员是否为系统超级管理员
        String loginSystemUserRoleCode = jdbcTemplate.queryForObject(QuerySystemLoginSqlConstant.QUERY_ROLE_CODE_SQL, String.class, loginSystemUserId);
        //创建permissions数组
        List<String> permissions;
        //校验是否为系统超级管理员权限
        if (SystemRoleConstant.ADMIN_ROLE_CODE.equals(loginSystemUserRoleCode)) {
            //直接以系统超级管理员权限查询对应的权限数据
            permissions = jdbcTemplate.queryForList(QuerySystemLoginSqlConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else {
            //普通后台登录人员权限查询
            permissions = jdbcTemplate.queryForList(QuerySystemLoginSqlConstant.QUERY_PERMISSION_SQL, String.class, loginSystemUserId);
        }
        //校验查询到的权限是否为空
        if (permissions.isEmpty()) {
            //为空，直接构建返回空集
            return Collections.emptySet();
        }
        //对permissions进行去重并构建返回数据结构
        return permissions.stream().distinct().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }


    /**
     * 登录类型为会员类型的类型
     *
     * @param username 登录的会员用户名
     * @return UserDetails
     */
    private UserDetails loadMemberUserByUsername(String username) {
        //查询用于登录的会员是否存在
        return jdbcTemplate.queryForObject(QueryMemberLoginSqlConstant.QUERY_MEMBER_SQL, (rs, rowNum) -> {
            //检验会员是否存在
            if (rs.wasNull()) {
                throw new UsernameNotFoundException("用户名" + username + "不存在！");
            }
            //得到会员id
            long memberLoginId = rs.getLong("id");
            //得到会员密码
            String password = rs.getString("password");
            //得到会员是否在使用中
            int status = rs.getInt("status");
            //构建用户类，并将会员id作为会员名，同时校验会员是否处于使用中
            return new User(String.valueOf(memberLoginId), password, status == 1, true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }, username, username);
    }
}
