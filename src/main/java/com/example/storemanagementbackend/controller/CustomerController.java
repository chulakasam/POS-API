package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.Dao.CustomerDataImpl;
import com.example.storemanagementbackend.Dto.CustomerDTO;
import com.example.storemanagementbackend.Utill.UtilProcess;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "",loadOnStartup = 2)
public class CustomerController extends HttpServlet {

    static Logger logger= LoggerFactory.getLogger(CustomerController.class);
    private Connection connection;



    @Override
    public void init() throws ServletException {

        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource)ctx.lookup("java:comp/env/jdbc/storeRegistration");
            this.connection = pool.getConnection();
        }catch (NamingException | SQLException e){
            logger.error("info failed message"+e.getMessage());
            e.printStackTrace();
        }
        logger.info("Initializing customer controller ");

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get student
        String nic = req.getParameter("nic");
        CustomerDataImpl customerData = new CustomerDataImpl();
        try(var writer=resp.getWriter()){
            CustomerDTO customerDTO = customerData.getCustomer(nic, connection);
            System.out.println(customerDTO);
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(customerDTO,writer);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("called doPost method ");
        //save student-------------
        if(!req.getContentType().toLowerCase().startsWith("application/json")||req.getContentType()==null){
            //send error---------------
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        try {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);//jsonb eken kranne json type data tika object ekath ekka bind karana eka..

            System.out.println("dopost"+customerDTO);
            CustomerDataImpl customerData = new CustomerDataImpl();
            customerData.saveCustomer(customerDTO,connection);
        } catch (JsonbException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerDataImpl customerData = new CustomerDataImpl();
        try (var writer = resp.getWriter()) {
            // Retrieve the NIC from the query parameter
            String nic = req.getParameter("nic");
            if (nic == null || nic.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "NIC is required");
                return;
            }

            // Parse the incoming JSON request
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO updateCustomer = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            // Update the customer information
            boolean isUpdated = customerData.updateCustomer(updateCustomer, connection, nic);
            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Update Failed");
            }
        } catch (JsonbException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing JSON data");
            throw new RuntimeException(e);
        }
    }




    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //delete customer
        String id = req.getParameter("nic");
        try {
            CustomerDataImpl customerData = new CustomerDataImpl();
            customerData.deleteCustomer(id,connection);

        } catch (JsonbException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }


}




