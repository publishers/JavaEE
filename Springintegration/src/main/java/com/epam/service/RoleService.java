package com.epam.service;

import com.epam.database.dao.RoleDAO;
import com.epam.database.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDAO roleDAO;

    public Role findRoleById(int id){
        return roleDAO.findOne(id);
    }

    public List<Role> findAll(){
        return roleDAO.findAll();
    }
}
