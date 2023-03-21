package cool.ange.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.domain.Notice;
import cool.ange.mapper.NoticeMapper;
import cool.ange.service.NoticeService;
/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: NoticeServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: todo
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

}
