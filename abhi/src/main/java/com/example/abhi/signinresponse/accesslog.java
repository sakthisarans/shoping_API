package com.example.abhi.signinresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
@AllArgsConstructor
@Data
@Getter@Setter
public class accesslog {
    @Field(targetType = FieldType.OBJECT_ID)@Id
    private String id;
    List<devicedatafordb> devicedata;
    public accesslog(){}
}
