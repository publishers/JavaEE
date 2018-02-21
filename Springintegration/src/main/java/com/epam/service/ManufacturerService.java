package com.epam.service;

import com.epam.database.dao.ManufacturerDAO;
import com.epam.database.entity.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerDAO manufacturerDAO;

    public ManufacturerDAO selectById(int id) {
        throw new UnsupportedOperationException();
    }

    public List<Manufacturer> selectAll() {
        return manufacturerDAO.findAll();
    }
}
