package com.epam.service;

import com.epam.database.dao.ManufacturerDAO;
import com.epam.database.entity.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerDAO manufacturerDAO;
//    @Autowired
//    private TransactionManager transactionManager;

    public ManufacturerDAO selectById(int id) {
//        return (ManufacturerDAO) transactionManager.execute(connection -> manufacturerDAO.select(connection, id));
        return null;
    }

    public List<Manufacturer> selectAll() {
//        return (List<Manufacturer>) transactionManager.execute(manufacturerDAO::selectAll);
        return Collections.emptyList();
    }
}
