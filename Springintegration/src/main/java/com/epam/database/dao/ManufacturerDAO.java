package com.epam.database.dao;

import com.epam.database.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerDAO extends JpaRepository<Manufacturer, Long> {
}
