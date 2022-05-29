package com.devthiagofurtado.cardapioqrcode.exception.handler;

import com.devthiagofurtado.cardapioqrcode.exception.ExceptionResponse;
import com.devthiagofurtado.cardapioqrcode.exception.InvalidJwtAuthenticationException;
import com.devthiagofurtado.cardapioqrcode.exception.ResourceBadRequestException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        String mensage = "";
        if(ex.getMessage().contains("constraint [uk_user_name]")){
            mensage = "Email já cadastrado.";
        } else {
            mensage = ex.getMessage();
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), mensage, request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> invalidJwtAuthenticationException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public final ResponseEntity<ExceptionResponse> resoureBadRequestException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> sqlException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
