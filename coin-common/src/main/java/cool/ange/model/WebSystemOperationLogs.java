package cool.ange.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: ange
 * @package: cool.ange.model
 * @className: WebSystemOperationLogs
 * @creationTime: 2023-03-05 19:44
 * @Version: v1.0
 * @description: Web系统操作日志
 */

@Data
@EqualsAndHashCode
public class WebSystemOperationLogs implements Serializable {

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 返回结果
     */
    private Object result;
}
