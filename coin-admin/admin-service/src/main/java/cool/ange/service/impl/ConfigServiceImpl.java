package cool.ange.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.domain.Config;
import cool.ange.mapper.ConfigMapper;
import cool.ange.service.ConfigService;
/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: ConfigServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: todo
*/
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService{

}
