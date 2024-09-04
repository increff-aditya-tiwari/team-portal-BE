package com.increff.teamer.pojo;


import com.increff.teamer.model.constant.EventCategory;
import com.increff.teamer.model.constant.RequestStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "claim_approval",uniqueConstraints = @UniqueConstraint(name = "claim_approval_unique",columnNames = {"claimId","approvalRequiredBy"}))
public class ClaimApprovalPojo extends AbstractVersionedPojo{
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long claimId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus approvalStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;
    @Column(nullable = false)
    private Long approvalRequiredBy;
    @Column(nullable = false)
    private Long approvalStage;
    @Column(nullable = true)
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public ClaimApprovalPojo(){

    }
    public ClaimApprovalPojo(Long claimId, RequestStatus approvalStatus, Long approvalRequiredBy, Long approvalStage,EventCategory eventCategory) {
        this.claimId = claimId;
        this.approvalStatus = approvalStatus;
        this.approvalRequiredBy = approvalRequiredBy;
        this.approvalStage = approvalStage;
        this.eventCategory = eventCategory;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public RequestStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(RequestStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getApprovalRequiredBy() {
        return approvalRequiredBy;
    }

    public void setApprovalRequiredBy(Long approvalRequiredBy) {
        this.approvalRequiredBy = approvalRequiredBy;
    }

    public Long getApprovalStage() {
        return approvalStage;
    }

    public void setApprovalStage(Long approvalStage) {
        this.approvalStage = approvalStage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
