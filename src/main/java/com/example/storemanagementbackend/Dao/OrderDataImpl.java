package com.example.storemanagementbackend.Dao;

import com.example.storemanagementbackend.Dto.OrderDTO;
import com.example.storemanagementbackend.Dto.OrderItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDataImpl implements OrderData {

    public boolean saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException {
        String insertOrderSQL = "INSERT INTO orders (order_id,nic, order_date, total_price, discount_percentage, sub_total_price, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertOrderItemSQL = "INSERT INTO order_items (order_id, code, item_name, item_price, order_qty, total) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderSQL);
             PreparedStatement orderItemStmt = connection.prepareStatement(insertOrderItemSQL)) {

            // Save the order details
            orderStmt.setString(1, orderDTO.getOrderId());
            orderStmt.setString(2, orderDTO.getCustomerId());
            orderStmt.setDate(3, orderDTO.getOrderDate());
            orderStmt.setDouble(4, orderDTO.getTotalPrice());
            orderStmt.setDouble(5, orderDTO.getDiscountPercentage());
            orderStmt.setDouble(6, orderDTO.getSubTotalPrice());
            orderStmt.setDouble(7, orderDTO.getBalance());
            int orderResult = orderStmt.executeUpdate();

            // Save each order item
            for (OrderItemDTO item : orderDTO.getOrderItems()) {
                orderItemStmt.setString(1, orderDTO.getOrderId());
                orderItemStmt.setString(2, item.getItemCode());
                orderItemStmt.setString(3, item.getItemName());
                orderItemStmt.setDouble(4, item.getItemPrice());
                orderItemStmt.setInt(5, item.getOrderQty());
                orderItemStmt.setDouble(6, item.getTotal());
                orderItemStmt.addBatch();
            }
            int[] orderItemsResult = orderItemStmt.executeBatch();

            return orderResult > 0 && orderItemsResult.length == orderDTO.getOrderItems().size();
        }
    }
}
