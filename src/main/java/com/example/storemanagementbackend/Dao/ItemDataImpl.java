package com.example.storemanagementbackend.Dao;

import com.example.storemanagementbackend.Dto.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDataImpl implements ItemData{

    static String SAVE_ITEM="INSERT INTO item(code,name,description,qty,unitPrice)VALUES(?,?,?,?,?) ";
    @Override
    public ItemDTO getItem(String id, Connection connection) {
       return null;
    }

    @Override
    public String saveItem(ItemDTO itemDTO, Connection connection) {
        try {
            PreparedStatement pstm = connection.prepareStatement(SAVE_ITEM);

            pstm.setString(1,itemDTO.getCode());
            pstm.setString(2,itemDTO.getName());
            pstm.setString(3,itemDTO.getDescription());
            pstm.setString(4,itemDTO.getQty());
            pstm.setString(5,itemDTO.getUnitPrice());
            if(pstm.executeUpdate()>0){
                return  "save successful";
            }else{
                return "save failed";
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteItem(String id, Connection connection) {
        return null;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO, Connection connection, String id) {
        return false;
    }
}
