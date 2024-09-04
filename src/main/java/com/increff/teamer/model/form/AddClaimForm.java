package com.increff.teamer.model.form;

import java.util.List;

public class AddClaimForm {
    private Long eventId;
    private List<AddExpenseForm> expenseDetails;

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
