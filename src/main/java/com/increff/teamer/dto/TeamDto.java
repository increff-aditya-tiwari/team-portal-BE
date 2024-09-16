package com.increff.teamer.dto;

import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.flowApi.TeamFlowApi;
import com.increff.teamer.model.data.TeamData;
import com.increff.teamer.model.data.UserUiData;
import com.increff.teamer.model.form.CreateTeamForm;
import com.increff.teamer.model.form.DeleteTeamMemberForm;
import com.increff.teamer.model.form.TeamUserMapForm;
import com.increff.teamer.model.form.UpdateRequestForm;
import com.increff.teamer.pojo.RequestDetailPojo;
import com.increff.teamer.pojo.TeamPojo;
import com.increff.teamer.util.ConvertUtil;
import com.increff.teamer.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamDto {
    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private TeamFlowApi teamFlowApi;
    @Autowired
    private ValidationUtil validationUtil;

    public TeamPojo createTeam(CreateTeamForm createTeamForm) throws CommonApiException {
        validationUtil.validateForm(createTeamForm);
        TeamPojo teamPojo = convertUtil.convertDataToPojo(createTeamForm,TeamPojo.class);
        return teamFlowApi.createTeam(teamPojo);
    }

    public List<TeamData> getAllTeamData() throws CommonApiException{
        return teamFlowApi.getAllTeamData();
    }

    public void teamJoinInvite(TeamUserMapForm teamUserMapForm) throws CommonApiException{
        validationUtil.validateForm(teamUserMapForm);
        teamFlowApi.teamJoinInvite(teamUserMapForm);
    }

    public void teamJoinRequest(TeamUserMapForm teamUserMapForm) throws CommonApiException{
        validationUtil.validateForm(teamUserMapForm);
        teamFlowApi.teamJoinRequest(teamUserMapForm.getTeamId());
    }

    public List<RequestDetailPojo> getAllOpenRequestsForTeam(Long teamId) throws CommonApiException{
        return teamFlowApi.getAllOpenRequestsForTeam(teamId);
    }
    public List<RequestDetailPojo> getAllOpenInvitesForTeam(Long teamId) throws CommonApiException{
        return teamFlowApi.getAllOpenInvitesForTeam(teamId);
    }

    public void updateTeamJoinRequestInvite(UpdateRequestForm updateRequestForm) throws CommonApiException{
        validationUtil.validateForm(updateRequestForm);
        teamFlowApi.updateTeamJoinRequestInvite(updateRequestForm);
    }

    public List<UserUiData> getAllTeamMembers(Long teamId) throws CommonApiException{
        return teamFlowApi.getAllTeamMembers(teamId);
    }

    public void removeTeamMember(DeleteTeamMemberForm deleteTeamMemberForm) throws CommonApiException{
        validationUtil.validateForm(deleteTeamMemberForm);
        teamFlowApi.removeTeamMember(deleteTeamMemberForm);
    }

    public List<Long> getTeamByUserId(Long userId) throws CommonApiException{
        return teamFlowApi.getTeamByUserId(userId);
    }

    public TeamPojo getTeamByTeamId(Long teamId) throws CommonApiException{
        return teamFlowApi.getTeamByTeamId(teamId);
    }
}
