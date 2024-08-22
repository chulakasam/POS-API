package com.example.storemanagementbackend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
        logger.info("Initializing student controller ");

    }

}
