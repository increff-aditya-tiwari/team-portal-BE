package com.increff.teamer.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "user_access_mapping",
        uniqueConstraints = @UniqueConstraint(name = "user_team_access",columnNames = {"userId","accessId","teamId"})
)
public class UserAccessMappingPojo extends AbstractVersionedPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long accessId;
    @Column(nullable = false)
    private Long accessAssignedBy;
    @Column(nullable = true)
    private Long teamId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
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

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public Long getAccessAssignedBy() {
        return accessAssignedBy;
    }

    public void setAccessAssignedBy(Long accessAssignedBy) {
        this.accessAssignedBy = accessAssignedBy;
    }
}
