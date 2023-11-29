package com.example.useractivity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Getter@Setter
public class products {
    private Object images;
    @NotBlank(message = "subCategory must contain value")
    private String subCategory;
    private boolean taxExempt;
    private boolean available;
    private int discountAmount;
    private boolean active;
    private int discountPercentage;
    private int price;
    private String description;
    @NotBlank(message = "name must contain value")
    private String name;
    private String currency;
    @NotBlank(message = "Category must contain value")
    private String category;
    @NotBlank(message = "sellerid must contain value")
    private String sellerid;
    private Object attributes;
    private String productid;
    @Min(value = 0,message = "Stockcount must contain value")
    private int stockcount;
    public products(){
    }
}


