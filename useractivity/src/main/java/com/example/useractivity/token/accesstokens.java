package com.example.useractivity.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@Data
@Getter@Setter
public class accesstokens {
    @Id
    private String id;
    @JsonIgnore
    List<String> roll;
    private token token;
   public accesstokens(){}

}
