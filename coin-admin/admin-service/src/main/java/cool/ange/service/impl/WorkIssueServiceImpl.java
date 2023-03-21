package cool.ange.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cool.ange.mapper.WorkIssueMapper;
import cool.ange.domain.WorkIssue;
import cool.ange.service.WorkIssueService;
/**
 * @author: ange
 * @package: cool.ange.service.impl
 * @className: WorkIssueServiceImpl
 * @creationTime: 2023-03-20 10:56
 * @Version: v1.0
 * @description: todo
*/
@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements WorkIssueService{

}
