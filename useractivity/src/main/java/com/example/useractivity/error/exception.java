package com.example.useractivity.error;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter@Setter
public class exception {
    private HttpStatus status;
    private String message;
    public exception(HttpStatus statu, String msg){
        this.status=statu;
        this.message=msg;

    }

}
