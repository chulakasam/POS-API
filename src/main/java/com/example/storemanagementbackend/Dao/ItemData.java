package com.example.storemanagementbackend.Dao;

import com.example.storemanagementbackend.Dto.CustomerDTO;
import com.example.storemanagementbackend.Dto.ItemDTO;

import java.sql.Connection;

public interface ItemData {


    ItemDTO getItem(String code , Connection connection);
    String saveItem(ItemDTO itemDTO, Connection connection);
    String deleteItem(String id,Connection connection);

    boolean updateItem(ItemDTO itemDTO,Connection connection, String id);
}
