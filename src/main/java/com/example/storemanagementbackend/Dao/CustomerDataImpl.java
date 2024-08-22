package com.example.storemanagementbackend.Dao;

import com.example.storemanagementbackend.Dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDataImpl implements CustomerData{
    static String SAVE_STUDENT="INSERT INTO customer (id,nic,name,address,tel,regDate)VALUES(?,?,?,?,?,?) ";


    @Override
    public CustomerDTO getCustomer(String id, Connection connection) {
        return null;
    }

    @Override
    public String saveCustomer(CustomerDTO customerDTO, Connection connection) {
        try {
            PreparedStatement pstm = connection.prepareStatement(SAVE_STUDENT);
            pstm.setString(1,customerDTO.getId());
            pstm.setString(2,customerDTO.getNic());
            pstm.setString(3,customerDTO.getName());
            pstm.setString(4,customerDTO.getAddress());
            pstm.setString(5,customerDTO.getTel());
            pstm.setString(6,customerDTO.getRegDate());
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
    public String deleteCustomer(String id, Connection connection) {
        return null;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO, Connection connection, String id) {
        return false;
    }
}
