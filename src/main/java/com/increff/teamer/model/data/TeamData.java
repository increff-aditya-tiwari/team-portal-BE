package com.increff.teamer.model.data;

import com.increff.teamer.pojo.RequestDetailPojo;
import com.increff.teamer.pojo.TeamPojo;

import java.util.List;

public class TeamData {
    private TeamPojo team;
    private List<RequestDetailPojo> requestList;
    private List<RequestDetailPojo> inviteList;

    public TeamPojo getTeam() {
        return team;
    }

    public void setTeam(TeamPojo team) {
        this.team = team;
    }

    public List<RequestDetailPojo> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<RequestDetailPojo> requestList) {
        this.requestList = requestList;
    }

    public TeamData(TeamPojo team, List<RequestDetailPojo> requestList, List<RequestDetailPojo> inviteList) {
        this.team = team;
        this.requestList = requestList;
        this.inviteList = inviteList;
    }

    public List<RequestDetailPojo> getInviteList() {
        return inviteList;
    }

    public void setInviteList(List<RequestDetailPojo> inviteList) {
        this.inviteList = inviteList;
    }
}
