package com.epam.malykhin.service;

import com.epam.malykhin.database.TransactionManager;
import com.epam.malykhin.database.dao.ManufacturerDAO;
import com.epam.malykhin.database.entity.Manufacturer;

import javax.servlet.ServletContext;
import java.util.List;

import static com.epam.malykhin.util.StaticTransformVariable.CONTEXT_LISTENER_TRANSACTION_MANAGER;
import static com.epam.malykhin.util.StaticTransformVariable.MANUFACTURER_DAO;

/**
 * Created by Serhii_Malykhin on 12/15/2016.
 */
public class ManufacturerService implements Service {
    private ManufacturerDAO manufacturerDAO;
    private TransactionManager transactionManager;

    @Override
    public void init(ServletContext context) {
        manufacturerDAO = (ManufacturerDAO) context.getAttribute(MANUFACTURER_DAO);
        transactionManager = (TransactionManager) context.getAttribute(CONTEXT_LISTENER_TRANSACTION_MANAGER);
    }

    public ManufacturerDAO selectById(int id) {
        return (ManufacturerDAO) transactionManager.execute(connection -> manufacturerDAO.select(connection, id));
    }

    public List<Manufacturer> selectAll() {
        return (List<Manufacturer>) transactionManager.execute(connection -> manufacturerDAO.selectAll(connection));
    }
}
