package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.Dao.OrderDataImpl;
import com.example.storemanagementbackend.Dto.OrderDTO;
import com.example.storemanagementbackend.Dto.OrderItemDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletException;
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

//@WebServlet(urlPatterns = "/order", loadOnStartup = 3)
public class OrderController extends HttpServlet {

    static Logger logger = LoggerFactory.getLogger(OrderController.class);
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/storeRegistration");
            this.connection = pool.getConnection();
        } catch (NamingException | SQLException e) {
            logger.error("Initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
        logger.info("Initializing OrderController");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Called doPost method for placing order");

        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        try {
            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);

            OrderDataImpl orderData = new OrderDataImpl();

            // Save order and order items in the database
            boolean orderSaved = orderData.saveOrder(orderDTO, connection);

            if (orderSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Order placed successfully with Order ID: " + orderDTO.getOrderId());
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to place the order");
            }
        } catch (JsonbException | SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing JSON data");
            throw new RuntimeException(e);
        }
    }
}
