package com.increff.teamer.model.form;

import com.increff.teamer.model.constant.RequestStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateRequestForm {

    @NotNull
    private Long requestDetailId;
    @NotNull
    private Long requestId;
    @NotNull
    private RequestStatus requestStatus;

    public Long getRequestDetailId() {
        return requestDetailId;
    }

    public void setRequestDetailId(Long requestDetailId) {
        this.requestDetailId = requestDetailId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
