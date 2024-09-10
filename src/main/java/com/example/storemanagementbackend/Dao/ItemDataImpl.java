package com.example.storemanagementbackend.Dao;

import com.example.storemanagementbackend.Dto.CustomerDTO;
import com.example.storemanagementbackend.Dto.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDataImpl implements ItemData{

    static String SAVE_ITEM="INSERT INTO item(code,name,description,qty,unitPrice)VALUES(?,?,?,?,?) ";
    static String GET_Item="SELECT * FROM item WHERE code=?";
    static String UPDATE_Item="UPDATE item SET  name=?,description=?,qty=?,unitPrice=? WHERE code=?";
    static String DELETE_Item="DELETE FROM item WHERE code=?";
    @Override
    public ItemDTO getItem(String id, Connection connection) {
        ItemDTO customerDTO = new ItemDTO();
        try {
            PreparedStatement pstm = connection.prepareStatement(GET_Item);
            pstm.setString(1, id);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                customerDTO.setCode(resultSet.getString("code"));
                customerDTO.setName(resultSet.getString("name"));
                customerDTO.setDescription(resultSet.getString("description"));
                customerDTO.setQty(resultSet.getString("qty"));
                customerDTO.setUnitPrice(resultSet.getString("unitPrice"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerDTO;
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
        try {
            PreparedStatement pstm = connection.prepareStatement(DELETE_Item);
            pstm.setString(1,id);
            if(pstm.executeUpdate()>0){
                return "DELETE SUCCESS!!";
            }else{
                return "DELETE FAILED";
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO, Connection connection, String id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(UPDATE_Item);

            pstm.setString(1, itemDTO.getName());
            pstm.setString(2, itemDTO.getDescription());
            pstm.setString(3, itemDTO.getQty());
            pstm.setString(4, itemDTO.getUnitPrice());
            ;
            pstm.setString(5, id);
            return pstm.executeUpdate()>0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
