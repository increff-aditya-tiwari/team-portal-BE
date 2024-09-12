package com.increff.teamer.model.form;

import jakarta.validation.constraints.NotNull;

public class DeleteTeamMemberForm {
    @NotNull
    private Long teamId;
    @NotNull
    private Long userId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
