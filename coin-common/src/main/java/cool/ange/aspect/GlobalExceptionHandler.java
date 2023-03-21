package cool.ange.aspect;

import cool.ange.extension.ApiException;
import cool.ange.model.ResponseResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: ange
 * @package: cool.ange.aspect
 * @className: GlobalExceptionHandler
 * @creationTime: 2023-03-15 17:58
 * @Version: v1.0
 * @description: 全局web的异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理ApiException异常
     * @param e ApiException
     * @return ResponseResult
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseResult<Object> handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return ResponseResult.fail(e.getErrorCode());
        }
        return ResponseResult.fail(e.getMessage());
    }

    /**
     * 处理MethodArgumentNotValidException异常
     * @param e MethodArgumentNotValidException
     * @return ResponseResult
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult<String> handleValidException(MethodArgumentNotValidException e) {
        return getStringResponseResult(e);
    }

    /**
     * 处理BindException异常
     * @param e BindException
     * @return ResponseResult
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseResult<String> handleValidException(BindException e) {
        return getStringResponseResult(e);
    }

    /**
     * 处理构建异常
     * @param e BindException
     * @return ResponseResult
     */
    @NotNull
    private ResponseResult<String> getStringResponseResult(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return ResponseResult.fail(message);
    }
}

