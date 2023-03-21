package cool.ange.extension;

import java.io.Serial;

/**
 * @author: ange
 * @package: cool.ange.extension
 * @className: ApiException
 * @creationTime: 2023-03-17 18:11
 * @Version: v1.0
 * @description: REST API 请求异常类
 */
public class ApiException  extends RuntimeException{
    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = -5885155226898287919L;

    /**
     * 错误码
     */
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
