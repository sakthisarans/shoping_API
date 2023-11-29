package com.example.abhi.accesstoken;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Setter@Getter
public class accesstokens {
    @Field(targetType = FieldType.OBJECT_ID)@Id
    private String id;
    accesstoken token;
    @JsonIgnore
    List<String> roll;
    List<accesstoken> accesstoken;
    public accesstokens(){}
}
