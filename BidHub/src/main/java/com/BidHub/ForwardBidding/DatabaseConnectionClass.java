package com.BidHub.ForwardBidding;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseConnectionClass {

    public static Connection connect() {
        Connection conn = null;
        try {
            // Obtain our environment naming context
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            
            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/EECS");
            
            // Allocate and use a connection from the pool
            conn = ds.getConnection();
        } catch (SQLException | NamingException e) {
            // Log or handle the exception
            e.printStackTrace();
        }
        return conn;
    }
}
