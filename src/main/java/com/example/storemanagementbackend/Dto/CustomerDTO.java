package com.example.storemanagementbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO implements Serializable {
    private String id;
    private String nic;
    private String name;
    private String address;
    private String tel;
    private String regDate;
}
