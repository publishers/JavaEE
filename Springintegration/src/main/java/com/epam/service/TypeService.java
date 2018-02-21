package com.epam.service;

import com.epam.database.dao.TypeDAO;
import com.epam.database.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeDAO typeDAO;


    public Type select(int id) {
        throw new UnsupportedOperationException();
    }

    public List<Type> selectAll() {
        return typeDAO.findAll();
    }
}
