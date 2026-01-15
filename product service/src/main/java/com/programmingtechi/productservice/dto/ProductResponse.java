package com.programmingtechi.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductResponse {


    private String id;
    private String name;
    private String description;
    private double price;

}
