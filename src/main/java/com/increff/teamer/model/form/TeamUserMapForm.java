package com.increff.teamer.model.form;

import java.util.List;

public class TeamUserMapForm {
    private Long teamId;
    private List<Long> userIds;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public TeamUserMapForm(){

    }

    @Override
    public String toString() {
        return "TeamUserMapForm{" +
                "teamId=" + teamId +
                ", userIds=" + userIds +
                '}';
    }

    public TeamUserMapForm(Long teamId, List<Long> userIds) {
        this.teamId = teamId;
        this.userIds = userIds;
    }
}
