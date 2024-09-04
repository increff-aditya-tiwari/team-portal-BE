package com.increff.teamer.dao;

import com.increff.teamer.pojo.AccessPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessDao extends JpaRepository<AccessPojo,Long> {
    public AccessPojo findByAccessId(Long accessId);
    public AccessPojo findByAccessName(String accessName);
}
