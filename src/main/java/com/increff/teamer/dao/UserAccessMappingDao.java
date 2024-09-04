package com.increff.teamer.dao;

import com.increff.teamer.pojo.UserAccessMappingPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccessMappingDao extends JpaRepository<UserAccessMappingPojo,Long> {
    public List<UserAccessMappingPojo> findAllByUserId(Long userId);
    public UserAccessMappingPojo findByUserIdAndAccessId(Long userId,Long accessId);
}
