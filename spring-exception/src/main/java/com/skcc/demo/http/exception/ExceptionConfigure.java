package com.skcc.demo.http.exception;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.skcc.demo.http.exception.ErrorResponse;

@ControllerAdvice
public class ExceptionConfigure extends ResponseEntityExceptionHandler{

	
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidGrant(HttpServletRequest request, Exception ige) throws URISyntaxException{
        return ResponseEntity.badRequest().body(new ErrorResponse(
                "InvalidGrantException",
                ige.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        ));
    }
     
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException e) {
        ModelAndView mnv = new ModelAndView("exceptionHandler");
        mnv.addObject("data", e.getMessage());
         
        return mnv;
    }	
}
