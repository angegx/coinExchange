package cool.ange.extension;

/**
 * @author: ange
 * @package: cool.ange.extension
 * @className: IErrorCode
 * @creationTime: 2023-03-17 18:10
 * @Version: v1.0
 * @description: REST API 错误码接口
 */
public interface IErrorCode {
    /**
     * 错误编码 -1、失败 0、成功
     */
    long getCode();

    /**
     * 错误描述
     */
    String getMsg();
}
