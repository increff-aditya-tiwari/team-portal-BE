package com.increff.teamer.dto;

import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.flowApi.ExpenseClaimFlowApi;
import com.increff.teamer.model.form.NewAddExpenseForm;
import com.increff.teamer.model.form.UpdateClaimApprovalForm;
import com.increff.teamer.pojo.ClaimApprovalPojo;
import com.increff.teamer.pojo.ClaimPojo;
import com.increff.teamer.pojo.ExpensePojo;
import com.increff.teamer.util.ConvertUtil;
import com.increff.teamer.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ClaimExpenseDto {
    @Autowired
    ExpenseClaimFlowApi expenseClaimFlowApi;
    @Autowired
    ValidationUtil validationUtil;
    @Autowired
    ConvertUtil convertUtil;
    public ClaimPojo addClaim(Long eventId) throws CommonApiException{
        return expenseClaimFlowApi.addClaim(eventId);
    }

    public ExpensePojo addExpense(NewAddExpenseForm newAddExpenseForm) throws CommonApiException, IOException {
        validationUtil.validateForm(newAddExpenseForm);
        return expenseClaimFlowApi.addNewExpense(newAddExpenseForm);
    }

    public void removeExpense(Long expenseId) throws CommonApiException{
        expenseClaimFlowApi.removeExpense(expenseId);
    }
    public ExpensePojo getExpenseById(Long expenseId) throws CommonApiException{
        return expenseClaimFlowApi.getExpenseById(expenseId);
    }

    public ExpensePojo updateExpense(ExpensePojo expensePojo) throws CommonApiException{
        return expenseClaimFlowApi.updateExpense(expensePojo);
    }

    public ClaimApprovalPojo updateClaimApproval(UpdateClaimApprovalForm updateClaimApprovalForm) throws CommonApiException{
        validationUtil.validateForm(updateClaimApprovalForm);
        return expenseClaimFlowApi.updateClaimApproval(updateClaimApprovalForm);
    }

    public List<ClaimPojo> getEventClaims(Long eventId) throws CommonApiException{
        return expenseClaimFlowApi.getEventClaims(eventId);
    }

    public List<ExpensePojo> getClaimExpense(Long claimId) throws CommonApiException{
        return expenseClaimFlowApi.getClaimExpenses(claimId);
    }

    public List<ClaimApprovalPojo> getAllClaimApprovals(Long claimId) throws CommonApiException{
        return expenseClaimFlowApi.getAllClaimApprovals(claimId);
    }

    public List<ClaimApprovalPojo> getAllPendingClaimsApprovalForUser() throws CommonApiException{
        return expenseClaimFlowApi.getAllPendingClaimsApprovalForUser();
    }

    public ClaimPojo getClaimById(Long claimId) throws CommonApiException{
        return expenseClaimFlowApi.getClaimById(claimId);
    }

    public byte[] downloadExpenseAttachment(Long expenseId) throws CommonApiException{
        return expenseClaimFlowApi.downloadExpenseAttachment(expenseId);
    }
}
