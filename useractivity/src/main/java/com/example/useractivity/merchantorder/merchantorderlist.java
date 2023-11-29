package com.example.useractivity.merchantorder;

import com.example.useractivity.shoppinghistory.product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;
@Data
@Getter@Setter@AllArgsConstructor
public class merchantorderlist {
    @Id
    @JsonIgnore
    private String id;
    List<merchantorder> order;
    public merchantorderlist(){};
}
