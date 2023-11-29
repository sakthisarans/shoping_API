package com.example.useractivity.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter@Setter
@Data
public class cartitems {
    String carname;
    String prodictid;
    int count;

    public cartitems() {

    }
}
