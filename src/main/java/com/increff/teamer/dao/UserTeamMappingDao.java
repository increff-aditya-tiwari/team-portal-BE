package com.increff.teamer.dao;

import com.increff.teamer.pojo.UserTeamMappingPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTeamMappingDao extends JpaRepository<UserTeamMappingPojo,Long> {
    public List<UserTeamMappingPojo> findAllByUserId(Long userId);
    public List<UserTeamMappingPojo> findAllByTeamId(Long teamId);
    public UserTeamMappingPojo findByUserIdAndTeamId(Long userId, Long teamId);
    public void deleteByUserIdAndTeamId(Long userId, Long teamId);
}
