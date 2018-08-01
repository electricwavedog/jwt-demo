package com.example.jwtdemo.web.common.error;

import com.example.jwtdemo.domain.model.APIResult;
import com.example.jwtdemo.web.common.error.exception.BusinessApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author liuyiqian
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessApiException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public APIResult handlerBusinessApiException(BusinessApiException e) {
        String message = messageSource.getMessage(e.getCode(), e.getArgs(), LocaleContextHolder.getLocale());
        logger.info("BusinessException: {}", message);
        return APIResult.failure().setMessage(message).setData(e.getData());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResult handlerException(Exception e) {
        logger.info("Exception: {}", e.getMessage());
        return APIResult.failure();
    }

    /**
     * 处理绑定数据异常
     * 表单提交，ModelAttribute接受
     *
     * @param ex ex
     * @param headers headers
     * @param status status
     * @param request request
     * @return APIResult
     */
    @Override
    public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                      HttpStatus status, WebRequest request) {
        logger.info("BindException: {}", ex.getMessage());
        return new ResponseEntity<>(APIResult.failure().setData(ex.getBindingResult().getAllErrors()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理绑定数据异常
     * json提交，RequestBody接受
     *
     * @param ex ex
     * @param headers headers
     * @param status status
     * @param request request
     * @return APIResult
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        logger.info("MethodArgumentNotValid: {}", ex.getMessage());
        return new ResponseEntity<>(APIResult.failure().setData(ex.getBindingResult().getAllErrors()),
                HttpStatus.BAD_REQUEST);
    }
}
