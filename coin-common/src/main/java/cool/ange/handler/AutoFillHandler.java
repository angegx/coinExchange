package cool.ange.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: ange
 * @package: cool.ange.handler
 * @className: AutoFillHandler
 * @creationTime: 2023-03-20 16:22
 * @Version: v1.0
 * @description: 自动填充数据
 */

@Component
public class AutoFillHandler implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     * <p>新增数据时要添加的为：</p>
     * <br/>1 创建人
     * <br/>2 创建时间
     * <br/>3 lastUpDatetime
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 获取当前操作的用户id
        Long userId = getCurrentUserId();
        // 创建人
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        // 创建时间
        this.strictInsertFill(metaObject, "created", Date.class, new Date());
        // 修改时间
        this.strictInsertFill(metaObject, "lastUpdateTime", Date.class, new Date());
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     * <br/>1 修改人
     * <br/>2 修改时间
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getCurrentUserId();
        // 修改人
        this.strictUpdateFill(metaObject, "modifyBy", Long.class, userId);
        // 修改时间
        this.strictUpdateFill(metaObject, "lastUpdateTime", Date.class, new Date());
    }

    /**
     * 获取当前操作的用户对象
     *
     * @return Long
     */
    private Long getCurrentUserId() {
        // 从安全的上下文里面获取用户的ud
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // userId ->Long  anonymousUser
            String s = authentication.getPrincipal().toString();
            String anonymousUser = "anonymousUser";
            //是因为用户没有登录访问时,就是这个用户
            if (anonymousUser.equals(s)) {
                return null;
            }
            return Long.valueOf(s);
        }
        return null;
    }
}
