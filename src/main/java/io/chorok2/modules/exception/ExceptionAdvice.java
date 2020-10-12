package io.chorok2.modules.exception;

import io.chorok2.modules.common.dto.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult defaultException(HttpServletRequest request, HttpServletResponse response) {
        return new ErrorResult(-1, "실패");
    }

    protected ErrorResult notFoundException(CNotFoundException e) {
        return new ErrorResult(-1, e.getMessage());
    }

}
