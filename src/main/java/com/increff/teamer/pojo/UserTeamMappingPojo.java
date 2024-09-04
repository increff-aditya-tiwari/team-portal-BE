package com.increff.teamer.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "user_team_mapping",
        uniqueConstraints = @UniqueConstraint(name = "user_team",columnNames = {"userId","teamId"})
)
public class UserTeamMappingPojo extends AbstractVersionedPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long teamId;
    private String userTeamRole;

    public UserTeamMappingPojo(){

    }
    public UserTeamMappingPojo(Long userId, Long teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getUserTeamRole() {
        return userTeamRole;
    }

    public void setUserTeamRole(String userTeamRole) {
        this.userTeamRole = userTeamRole;
    }
}
