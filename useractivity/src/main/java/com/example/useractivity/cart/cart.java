package com.example.useractivity.cart;

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
@Getter@Setter
public class cart {
    @Field(targetType = FieldType.OBJECT_ID)@Id@JsonIgnore
    private String id;
    List<cartitems> cartitems;
    public cart() {
    }
}