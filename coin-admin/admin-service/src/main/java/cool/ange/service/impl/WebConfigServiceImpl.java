package cool.ange.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.domain.WebConfig;
import cool.ange.mapper.WebConfigMapper;
import cool.ange.service.WebConfigService;
/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: WebConfigServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: todo
*/
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService{

}
