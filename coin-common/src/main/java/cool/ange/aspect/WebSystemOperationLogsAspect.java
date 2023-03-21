package cool.ange.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import cool.ange.model.WebSystemOperationLogs;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: ange
 * @package: cool.ange.aspect
 * @className: WebSystemOperationLogsAspect
 * @creationTime: 2023-03-05 19:47
 * @Version: v1.0
 * @description: web系统认证日志切面
 * <p>日志记录：环绕通知：方法执行之前、之后</p>
 */

@Component
@Aspect
@Slf4j
@Order(1)
public class WebSystemOperationLogsAspect {

    /**
     * 定义切入点<br/>
     * controller 包里面所有类，类里面的所有方法 都有该切面
     */
    @Pointcut("execution(* cool.ange.controller.*.*(..))")
    public void webSystemOperationLogs() {
    }

    /**
     * 记录日志的环绕通知
     *
     * @param proceedingJoinPoint 契入点
     * @return Object
     * @throws Throwable Throwable类是Java语言中所有错误或异常的超类，是对所有异常进行整合的一个普通类
     */
    @Around("webSystemOperationLogs()")
    public Object recodeWebSystemOperationLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        //创建webSystemOperationLogs对象
        WebSystemOperationLogs webSystemOperationLogs = new WebSystemOperationLogs();
        //记录开始时间
        long start = System.currentTimeMillis();
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        //记录结束时间
        long end = System.currentTimeMillis();
        //记录接口调用用时
        webSystemOperationLogs.setSpendTime((int) (end - start) / 1000);
        //获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        //获取安全消息的上下文
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //记录请求的uri
        webSystemOperationLogs.setUri(request.getRequestURI());
        //获取url
        String url = request.getRequestURL().toString();
        //记录请求的url
        webSystemOperationLogs.setUrl(url);
        // 记录请求的IP地址和请求的端口 http://ip:port/
        webSystemOperationLogs.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath()));
        //记录用户的id
        webSystemOperationLogs.setUsername(authentication == null ? "anonymous" : authentication.getPrincipal().toString());
        //记录ip地址
        webSystemOperationLogs.setIp(request.getRemoteAddr());
        // 获取方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 获取类的名称
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        //获取执行方法
        Method method = signature.getMethod();
        // 因为我们会使用Swagger 这工具，我们必须在方法上面添加@ApiOperation(value="")该注解 获取ApiOperation
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        //记录文档信息
        webSystemOperationLogs.setDescription(annotation == null ? "no desc" : annotation.value());
        // cool.ange.controller.UserController.login() 记录执行的方法名称
        webSystemOperationLogs.setMethod(targetClassName + "." + method.getName());
        //{"key_参数的名称":"value_参数的值"} 记录方法的参数
        webSystemOperationLogs.setParameter(getMethodParameter(method, proceedingJoinPoint.getArgs())); //{"key_参数的名称":"value_参数的值"}
        //记录返回体
        webSystemOperationLogs.setResult(result);
        log.info(JSON.toJSONString(webSystemOperationLogs,true));
        return result;
    }

    /**
     * 获取方法的执行参数
     *
     * @param method 方法
     * @param args   参数
     * @return {"key_参数的名称":"value_参数的值"}
     */
    private Object getMethodParameter(Method method, Object[] args) {
        //创建methodParametersWithValues hashMap集合
        Map<String, Object> methodParametersWithValues = new HashMap<>();
        //获取LocalVariableTableParameterNameDiscoverer对象
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        // 方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        //循环每一个参数，并记录
        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            //判断是否为受限的支持类型
            if (parameterNames[i].equals("password") || parameterNames[i].equals("file")) {
                //记录参数
                methodParametersWithValues.put(parameterNames[i], "受限的支持类型");
            } else {
                //记录参数
                methodParametersWithValues.put(parameterNames[i], args[i]);
            }
        }
        //返回记录参数的集合
        return methodParametersWithValues;
    }
}
