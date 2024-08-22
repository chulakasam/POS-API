package com.example.storemanagementbackend.Dao;

import com.example.storemanagementbackend.Dto.CustomerDTO;

import java.sql.Connection;

public interface CustomerData {
    CustomerDTO getCustomer(String id, Connection connection);
    String saveCustomer(CustomerDTO customerDTO, Connection connection);
    String deleteCustomer(String id,Connection connection);

    boolean updateCustomer(CustomerDTO customerDTO,Connection connection, String id);
}
