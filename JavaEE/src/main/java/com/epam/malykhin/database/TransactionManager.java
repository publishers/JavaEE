package com.epam.malykhin.database;

import com.epam.malykhin.database.exception.BusinessException;
import com.epam.malykhin.database.transaction.TransactionOperation;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Serhii_Malykhin on 12/7/2016.
 * <p>
 * Response for transaction
 */
public class TransactionManager<T> {
    private static final Logger LOG = Logger.getLogger(TransactionManager.class);
    private JdbcConnectionHolder connectionHolder;

    public TransactionManager(JdbcConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    public <T> T execute(TransactionOperation<T> transactionOperation) {
        T result = null;
        Connection connection = null;
        try {
            connection = connectionHolder.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            result = transactionOperation.doInTransaction(connection);
            connection.commit();
            connection.close();
        } catch (SQLException exc) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                LOG.warn("problems encoutered during rollback", ex);
            }
            throw new BusinessException(exc);
        }
        return result;
    }
}
