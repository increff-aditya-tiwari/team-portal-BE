package com.increff.teamer.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "teams",uniqueConstraints = @UniqueConstraint(columnNames = {"teamName"}))
public class TeamPojo extends AbstractVersionedPojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long teamId;
    @Column(nullable = false,unique = true)
    private String teamName;
    @Column(nullable = false)
    private Long createdBy;

    @Column(length = 5000)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
