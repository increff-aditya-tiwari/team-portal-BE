package com.increff.teamer.pojo;

import com.increff.teamer.model.constant.EventCategory;
import com.increff.teamer.model.constant.RequestStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "claims", uniqueConstraints = @UniqueConstraint(name = "unique_claim",columnNames = {"eventId","claimId"}))
public class ClaimPojo extends AbstractVersionedPojo{
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;
    @Column(nullable = false)
    private Long eventId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus claimStatus;
    @Column(nullable = false)
    private Long totalExpenseAmount;
    @Column(nullable = false)
    private Long addedBy;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public Long getEventId() {
        return eventId;
    }
    public ClaimPojo(){

    }
    public ClaimPojo(Long eventId, RequestStatus claimStatus, Long totalExpenseAmount, Long addedBy) {
        this.eventId = eventId;
        this.claimStatus = claimStatus;
        this.totalExpenseAmount = totalExpenseAmount;
        this.addedBy = addedBy;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }



    public RequestStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(RequestStatus claimStatus) {
        this.claimStatus = claimStatus;
    }

    public Long getTotalExpenseAmount() {
        return totalExpenseAmount;
    }

    public void setTotalExpenseAmount(Long totalExpenseAmount) {
        this.totalExpenseAmount = totalExpenseAmount;
    }

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }
}
