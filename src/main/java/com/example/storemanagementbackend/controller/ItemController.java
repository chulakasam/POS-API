package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.Dao.CustomerDataImpl;
import com.example.storemanagementbackend.Dao.ItemDataImpl;
import com.example.storemanagementbackend.Dto.CustomerDTO;
import com.example.storemanagementbackend.Dto.ItemDTO;
import com.example.storemanagementbackend.Utill.UtilProcess;
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


@WebServlet(urlPatterns = "/item",loadOnStartup = 2)
public class ItemController extends HttpServlet {

    static Logger logger= LoggerFactory.getLogger(ItemController.class);

    private Connection connection;

    @Override
    public void init() throws ServletException {

       /* try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource)ctx.lookup("java:comp/env/jdbc/itemRegistration");
            this.connection = pool.getConnection();
        }catch (NamingException | SQLException e){
            logger.error("info failed message"+e.getMessage());
            e.printStackTrace();
        }
        logger.info("Initializing item controller ");

        */

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
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);//jsonb eken kranne json type data tika object ekath ekka bind karana eka..

            System.out.println("dopost"+itemDTO);
            ItemDataImpl itemData = new ItemDataImpl();
            itemData.saveItem(itemDTO,connection);
        } catch (JsonbException e) {
            throw new RuntimeException(e);
        }
    }
}


