package com.epam.malykhin.service;

import com.epam.malykhin.database.TransactionManager;
import com.epam.malykhin.database.dao.TypeDAO;
import com.epam.malykhin.database.entity.Type;

import javax.servlet.ServletContext;
import java.util.List;

import static com.epam.malykhin.util.StaticTransformVariable.CONTEXT_LISTENER_TRANSACTION_MANAGER;
import static com.epam.malykhin.util.StaticTransformVariable.TYPE_DAO;

/**
 * Created by Serhii_Malykhin on 12/15/2016.
 */
public class TypeService implements Service {
    private TypeDAO typeDAO;
    private TransactionManager transactionManager;

    public void init(ServletContext context) {
        typeDAO = (TypeDAO) context.getAttribute(TYPE_DAO);
        transactionManager = (TransactionManager) context.getAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER);
    }

    public Type select(int id) {
        return (Type) transactionManager.execute(connection -> typeDAO.select(connection, id));
    }

    public List<Type> selectAll() {
        return (List<Type>) transactionManager.execute(connection -> typeDAO.selectAll(connection));
    }
}
