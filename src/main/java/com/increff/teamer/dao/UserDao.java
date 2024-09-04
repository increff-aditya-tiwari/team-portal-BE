package com.increff.teamer.dao;

import com.increff.teamer.pojo.UserPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserPojo,Long> {
    public UserPojo findByUserName(String username);
    public UserPojo findByUserId(Long userId);
    public List<UserPojo> findAllByUserIdIn(List<Long> userIds);
    public UserPojo findByEmail(String email);
}
