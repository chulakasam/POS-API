package com.example.storemanagementbackend.Dao;

import com.example.storemanagementbackend.Dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderData {
     boolean saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException;
}
