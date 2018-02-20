package com.epam.service;

import com.epam.database.entity.Type;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TypeService {
//    @Autowired
//    private TypeDAO typeDAO;


    public Type select(int id) {
//        return (Type) transactionManager.execute(connection -> typeDAO.select(connection, id));
        return new Type();
    }

    public List<Type> selectAll() {
//        return (List<Type>) transactionManager.execute(connection -> typeDAO.selectAll(connection));
        return Collections.emptyList();
    }
}
