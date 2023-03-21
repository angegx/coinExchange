package cool.ange.model;

import cool.ange.constant.ResponseResultConstants;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: ange
 * @package: cool.ange.model
 * @className: ResponseResult
 * @creationTime: 2023-03-05 17:21
 * @Version: v1.0
 * @description: 统一返回值类
 */
@Data
public class ResponseResult<T> implements Serializable {

    /**
     * 串行 lint 标志结合使用，以对类的与序列化相关的成员执行编译时检查
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 成功响应码
     */
    private static final int SUCCESS = ResponseResultConstants.SUCCESS;

    /**
     * 成功响应消息
     */
    private static final String SUCCESS_MESSAGE = ResponseResultConstants.SUCCESS_MESSAGE;

    /**
     * 失败的响应码
     */
    private static final int FAIL = ResponseResultConstants.FAIL;

    /**
     * 失败的响应消息
     */
    private static final String FAIL_MESSAGE = ResponseResultConstants.FAIL_MESSAGE;

    /**
     * 统一返回值响应码
     */
    private int code;

    /**
     * 统一返回值响应消息
     */
    private String message;

    /**
     * 统一返回值响应数据
     */
    private T data;

    /**
     * responseResult 统一返回值
     * @param code 响应码
     * @param message 响应消息
     * @param data 响应数据
     * @return ResponseResult
     * @param <T> 响应数据的类型
     */
    private static <T> ResponseResult<T> responseResult(int code ,String message, T data){
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 成功响应，无需任何参数。默认code为200、message为"success"、没有响应的数据。
     * @return ResponseResult
     * @param <T> 响应数据的数据类型
     */
    public static <T> ResponseResult<T> success(){
        return responseResult(SUCCESS,SUCCESS_MESSAGE,null);
    }

    /**
     * 成功响应，需要响应成功的message参数。默认code为200、没有响应的数据。
     * @param message 响应成功的message
     * @return ResponseResult
     * @param <T> 响应数据的数据类型
     */
    public static <T> ResponseResult<T> success(String message){
        return responseResult(SUCCESS,message,null);
    }

    /**
     * 成功响应，需要响应成功的message和需要响应的data。默认code为200。
     * @param message 响应成功的message
     * @param data 响应成功的data
     * @return ResponseResult
     * @param <T> 响应数据的数据类型
     */
    public static <T> ResponseResult<T> success(String message,T data){
        return responseResult(SUCCESS,message,data);
    }

    /**
     * 失败响应，无需任何参数。默认code为200、message为"fail"、没有响应的数据。
     * @return ResponseResult
     * @param <T> 响应数据的数据类型
     */
    public static <T> ResponseResult<T> fail(){
        return responseResult(FAIL,FAIL_MESSAGE,null);
    }

    /**
     * 失败响应，需要响应失败的message参数。默认code为200、没有响应的数据。
     * @param message 响应成功的message
     * @return ResponseResult
     * @param <T> 响应数据的数据类型
     */
    public static <T> ResponseResult<T> fail(String message){
        return responseResult(FAIL,message,null);
    }

    /**
     * 失败响应，需要响应失败的message参数。默认code为200、没有响应的数据。
     * @param message 响应成功的消息
     * @return ResponseResult
     * @param <T> 响应数据的数据类型
     */
    public static <T> ResponseResult<T> fail(Object message){
        return responseResult(FAIL,message.toString(),null);
    }

    /**
     * 失败响应，需要响应失败的message和需要响应的data。默认code为200。
     * @param message 响应成功的message
     * @param data 响应成功的data
     * @return ResponseResult
     * @param <T> 响应数据的数据类型
     */
    public static <T> ResponseResult<T> fail(String message,T data){
        return responseResult(FAIL,message,data);
    }

    /**
     * 自定义的统一返回值，需要自定义code、message、data。
     * @param code 响应码
     * @param message 响应消息
     * @param data 响应数据
     * @return ResponseResult
     * @param <T> 响应数据的数据类型
     */
    public static <T> ResponseResult<T> customizeResponseResult(int code ,String message, T data) {
        return responseResult(code,message,data);
    }


}
