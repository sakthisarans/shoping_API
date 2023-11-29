package com.example.shopping.products;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Data@Getter@Setter
public class multiproduct {
    @Valid
    List<products> products=new ArrayList<>();
    public multiproduct(){}
}
