package com.increff.teamer.model.form;

import com.increff.teamer.model.constant.RequestStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateClaimApprovalForm {
    @NotNull
    private Long claimApprovalId;
    @NotNull
    private RequestStatus approvalStatus;
    @NotEmpty
    @NotNull
    private String remarks;

    public Long getClaimApprovalId() {
        return claimApprovalId;
    }

    public void setClaimApprovalId(Long claimApprovalId) {
        this.claimApprovalId = claimApprovalId;
    }

    public RequestStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(RequestStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
