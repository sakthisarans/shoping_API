package com.example.shopping.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
@Getter@Setter
public class exception {
    private int status;
    private String message;
    exception(int statu,String msg){
        this.status=statu;
        this.message=msg;

    }

}
