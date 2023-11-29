package com.example.shopping.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Getter@Setter
public class products {
    @Id@JsonIgnore
    private String id;
    @JsonIgnore
    private String creator;
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
    private String sellerid;
    @JsonIgnore
    private String activationDate;
    private Object attributes;
    @NotBlank(message = "product id must contain value")
    private String productid;
    @Min(value = 0,message = "Stockcount must contain value")
    private int stockcount;
    public products(){
        this.activationDate= LocalDateTime.now().toString();
    }
}
