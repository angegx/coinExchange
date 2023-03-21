package cool.ange.feign;

import cool.ange.constant.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: ange
 * @package: cool.ange.feign
 * @className: OAuth2FeignConfig
 * @creationTime: 2023-03-20 11:58
 * @Version: v1.0
 * @description: OAuth2FeignConfig
 */

@Slf4j
public class OAuth2FeignConfig implements RequestInterceptor {

    /**
     * 调用每个请求。 使用提供的 {@link RequestTemplate} 上的方法添加数据。
     *
     * @param requestTemplate 请求模板
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 1 我们可以从request的上下文环境里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String header;
        if (requestAttributes == null) {
            log.info("没有请求的上下文,故无法进行token的传递");
            header = "bearer " + Constants.INSIDE_TOKEN;
        } else {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            // 获取我们请求上下文的头里面的AUTHORIZATION
            header = request.getHeader(HttpHeaders.AUTHORIZATION);
        }

        if (StringUtils.hasText(header)) {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, header);
            log.info("本次token传递成功,token的值为:{}", header);
        }
    }
}
