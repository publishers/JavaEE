package com.epam.database.dao;

import com.epam.database.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsDAO extends JpaRepository<Goods, Integer> {

}
