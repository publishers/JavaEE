package com.epam.database.dao;

import com.epam.database.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDAO extends JpaRepository<Type, Long> {
    Type findBy(int id);
}
