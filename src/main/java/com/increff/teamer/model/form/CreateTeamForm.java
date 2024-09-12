package com.increff.teamer.model.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateTeamForm {
    @NotEmpty
    @NotNull
    private String teamName;
    @NotEmpty
    @NotNull
    private String description;


    public String getTeamName() {
        return teamName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

}
