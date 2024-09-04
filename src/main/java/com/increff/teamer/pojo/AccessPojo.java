package com.increff.teamer.pojo;

import com.increff.teamer.model.constant.AccessType;
import jakarta.persistence.*;
import org.hibernate.mapping.Value;

@Entity
@Table(name = "access_control")
public class AccessPojo extends AbstractVersionedPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long accessId;
    @Column(nullable = false,unique = true)
    private String accessName;
    @Column(nullable = false,unique = true)
    private Long accessPriority;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccessType accessType;

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public Long getAccessPriority() {
        return accessPriority;
    }

    public void setAccessPriority(Long accessPriority) {
        this.accessPriority = accessPriority;
    }
}
