package com.increff.teamer.dao;

import com.increff.teamer.pojo.TeamPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamDao extends JpaRepository<TeamPojo,Long> {
    public TeamPojo findByTeamId(Long teamId);
    public TeamPojo findByTeamName(String teamName);

}
