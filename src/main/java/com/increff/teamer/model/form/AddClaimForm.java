package com.increff.teamer.model.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AddClaimForm {
    @NotNull
    private Long eventId;
    @NotNull
    @NotEmpty
    private List<AddExpenseForm> expenseDetails;
    @NotNull
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<AddExpenseForm> getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(List<AddExpenseForm> expenseDetails) {
        this.expenseDetails = expenseDetails;
    }
}
