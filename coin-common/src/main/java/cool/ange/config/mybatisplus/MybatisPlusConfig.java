package cool.ange.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ange
 * @package: cool.ange.config.mybatisPlus
 * @className: MybatisPlusConfig
 * @creationTime: 2023-03-05 09:35
 * @Version: v1.0
 * @description: mybatis-plus相关配置类
 */
@Configuration
@MapperScan("cool.ange.mapper")
public class MybatisPlusConfig {


    /**
     * 乐观锁 分页插件设置
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //配置分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //设置乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 主键序列的生成
     * ID_WORK 数字
     * ID_WORK_STR 字符串
     */
    @Bean
    public IKeyGenerator iKeyGenerator() {
        return new H2KeyGenerator();
    }
}
