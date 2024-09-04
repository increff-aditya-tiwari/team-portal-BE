package com.increff.teamer.controller;


import com.increff.teamer.dto.TeamDto;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.data.UserUiData;
import com.increff.teamer.model.form.CreateTeamForm;
import com.increff.teamer.model.form.DeleteTeamMemberForm;
import com.increff.teamer.model.form.TeamUserMapForm;
import com.increff.teamer.model.form.UpdateRequestForm;
import com.increff.teamer.pojo.RequestDetailPojo;
import com.increff.teamer.pojo.TeamPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RequestMapping(value = "team")
public class TeamController {

    @Autowired
    TeamDto teamDto;

    @PostMapping("/create")
    public TeamPojo createTeam(@RequestBody CreateTeamForm createUserForm) throws CommonApiException {
        return teamDto.createTeam(createUserForm);
    }

    @GetMapping("/getAll")
    public List<TeamPojo> getAllTeam() throws CommonApiException {
        return teamDto.getAllTeam();
    }

    @PostMapping("/invite-user")
    public void mapUserTeam(@RequestBody TeamUserMapForm teamUserMapForm) throws CommonApiException{
//        System.out.println("this is form "+teamUserMapForm.toString());
        teamDto.teamJoinInvite(teamUserMapForm);
    }

    @PostMapping("/join-request")
    public void TeamJoinRequest(@RequestBody TeamUserMapForm teamUserMapForm) throws CommonApiException{
        teamDto.teamJoinRequest(teamUserMapForm);
    }

    @GetMapping("/join-requests/{teamId}")
    public List<RequestDetailPojo> getAllRequestsForTeam(@PathVariable("teamId") Long teamId) throws CommonApiException{
        return  teamDto.getAllOpenRequestsForTeam(teamId);
    }
    @GetMapping("/join-invites/{teamId}")
    public List<RequestDetailPojo> getAllInvitesFromTeam(@PathVariable("teamId") Long teamId) throws CommonApiException{
        return teamDto.getAllOpenInvitesForTeam(teamId);
    }

    @PostMapping("/join-request-update")
    public void updateJoinTeamRequest(@RequestBody UpdateRequestForm updateRequestForm) throws CommonApiException {
        teamDto.updateTeamJoinRequestInvite(updateRequestForm);
    }

    @GetMapping("/get-members/{teamId}")
    public List<UserUiData> getAllTeamMember(@PathVariable("teamId") Long teamId)throws CommonApiException{
        return teamDto.getAllTeamMembers(teamId);
    }

    @PostMapping("/remove-member")
    public void removeTeamMember(@RequestBody DeleteTeamMemberForm deleteTeamMemberForm) throws CommonApiException {
//        System.out.println("this is team ID "+deleteTeamMemberForm.getTeamId()+"this is userId"+deleteTeamMemberForm.getUserId());
        teamDto.removeTeamMember(deleteTeamMemberForm);
    }

    @GetMapping("/get-user-teamIds/{userId}")
    public List<Long> getUserTeamList(@PathVariable("userId") Long userId) throws CommonApiException{
        return teamDto.getTeamByUserId(userId);
    }
}
