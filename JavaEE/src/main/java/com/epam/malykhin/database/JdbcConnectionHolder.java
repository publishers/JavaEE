package com.epam.malykhin.database;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Serhii_Malykhin on 12/7/2016.
 * <p>
 * Response for connection to database
 */
public class JdbcConnectionHolder {
    private static final Logger LOG = Logger.getLogger(JdbcConnectionHolder.class);
    private DataSource dataSource;

    public JdbcConnectionHolder() {
        init();
    }

    private void init() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/shopcakes");
            getConnection();
        } catch (NamingException e) {
            LOG.warn("NamingException: \n" + e);
        } catch (SQLException e) {
            LOG.warn("SQLException: \n" + e);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
