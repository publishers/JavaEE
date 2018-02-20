package com.epam.database.dao;

import com.epam.database.entity.UserBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBanDAO extends JpaRepository<UserBan, Long> {
}
