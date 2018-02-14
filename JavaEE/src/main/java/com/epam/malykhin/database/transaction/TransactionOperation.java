package com.epam.malykhin.database.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Serhii_Malykhin on 12/7/2016.
 */
public interface TransactionOperation<T> {
    T doInTransaction(Connection connection) throws SQLException;
}