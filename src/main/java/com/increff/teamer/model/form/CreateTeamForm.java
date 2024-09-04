package com.increff.teamer.model.form;

public class CreateTeamForm {
    private String teamName;
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
