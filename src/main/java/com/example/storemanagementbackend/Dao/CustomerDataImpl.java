package com.example.storemanagementbackend.Dao;

import com.example.storemanagementbackend.Dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDataImpl implements CustomerData{
    static String SAVE_STUDENT="INSERT INTO customer (id,nic,name,address,tel,regDate)VALUES(?,?,?,?,?,?) ";
    static String GET_STUDENT="SELECT * FROM customer WHERE nic=?";
    static String UPDATE_STUDENT="UPDATE customer SET  nic=?name=?,address=?,tel=?,regDate=? WHERE id=?";

    @Override
    public CustomerDTO getCustomer(String nic, Connection connection) {
        CustomerDTO customerDTO = new CustomerDTO();
        try {
            PreparedStatement pstm = connection.prepareStatement(GET_STUDENT);
            pstm.setString(1, nic);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                customerDTO.setNic(resultSet.getString("nic"));
                customerDTO.setName(resultSet.getString("name"));
                customerDTO.setAddress(resultSet.getString("address"));
                customerDTO.setTel(resultSet.getString("tel"));
                customerDTO.setRegDate(resultSet.getString("regDate"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerDTO;
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
        try {
            PreparedStatement pstm = connection.prepareStatement(UPDATE_STUDENT);
            pstm.setString(1, customerDTO.getNic());
            pstm.setString(2, customerDTO.getName());
            pstm.setString(3, customerDTO.getAddress());
            pstm.setString(4, customerDTO.getTel());
            pstm.setString(5, customerDTO.getRegDate());
            ;
            pstm.setString(6, id);
            return pstm.executeUpdate()>0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
