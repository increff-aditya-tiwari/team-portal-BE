package com.increff.teamer.api;

import com.increff.teamer.dao.TeamDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.form.CreateTeamForm;
import com.increff.teamer.pojo.TeamPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TeamApi {

    @Autowired
    private TeamDao teamDao;

    public TeamPojo saveTeam(TeamPojo teamPojo) throws CommonApiException{
        validateCreateTeam(teamPojo);
        return teamDao.save(teamPojo);
    };

    public List<TeamPojo> getAllTeam() throws CommonApiException{
        return teamDao.findAll();
    }

    private void validateCreateTeam(TeamPojo teamPojo) throws CommonApiException{
        TeamPojo existingTeamByName = teamDao.findByTeamName(teamPojo.getTeamName());
//        System.out.println("this is team name "+teamPojo.getTeamName() + " and this is existing team name " + existingTeamByName);
        if(existingTeamByName != null){
            throw new CommonApiException(HttpStatus.FOUND,"Team With this Name is Already Present");
        }
    }

    public TeamPojo isTeamValid(Long teamId) throws CommonApiException{
        TeamPojo teamPojo = teamDao.findByTeamId(teamId);
        if(teamPojo == null){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Team with team id "+ teamId +" not present");
        }
        return teamPojo;
    }
}
