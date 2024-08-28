package com.example.storemanagementbackend.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderItemDTO {

    private String itemCode;
    private String itemName;
    private double itemPrice;
    private int orderQty;
    private double total;

    // Getters and setters...

}
