package com.example.storemanagementbackend.Dto;

import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class OrderDTO {

    private String orderId;
    private String customerId;
    private Date orderDate;
    private double totalPrice;
    private double discountPercentage;
    private double subTotalPrice;
    private double balance;
    private List<OrderItemDTO> orderItems;

    // Getters and setters...

}
