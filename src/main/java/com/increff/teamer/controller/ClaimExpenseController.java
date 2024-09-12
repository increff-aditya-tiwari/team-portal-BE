package com.increff.teamer.controller;

import com.increff.teamer.dto.ClaimExpenseDto;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.form.NewAddExpenseForm;
import com.increff.teamer.model.form.UpdateClaimApprovalForm;
import com.increff.teamer.pojo.ClaimApprovalPojo;
import com.increff.teamer.pojo.ClaimPojo;
import com.increff.teamer.pojo.ExpensePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        allowCredentials = "true"
)
//@RequestMapping(value = "claim")
public class ClaimExpenseController {

    @Autowired
    ClaimExpenseDto claimExpenseDto;

    @PostMapping(value = "claim/add/{eventId}")
    public ClaimPojo addClaim(@PathVariable("eventId") Long eventId) throws CommonApiException {
        return claimExpenseDto.addClaim(eventId);
    }

    @PostMapping(value = "expense/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ExpensePojo addFileExpense(@ModelAttribute NewAddExpenseForm newAddExpenseForm) throws IOException, CommonApiException {
        return claimExpenseDto.addExpense(newAddExpenseForm);
    }

    @PostMapping(value = "expense/remove/{expenseId}")
    public void removeExpense(@PathVariable("expenseId") Long expenseId) throws CommonApiException {
        claimExpenseDto.removeExpense(expenseId);
    }

    @GetMapping("expense/get/{expenseId}")
    public ExpensePojo getExpenseById(@PathVariable("expenseId") Long expenseId) throws CommonApiException{
        return claimExpenseDto.getExpenseById(expenseId);
    }

    @PostMapping("expense/update")
    public ExpensePojo updateExpense(@RequestBody ExpensePojo expensePojo) throws CommonApiException{
        return claimExpenseDto.updateExpense(expensePojo);
    }

    @PostMapping("claim/approval-update")
    public ClaimApprovalPojo updateClaimApproval(@RequestBody UpdateClaimApprovalForm updateClaimApprovalForm) throws CommonApiException{
        System.out.println(updateClaimApprovalForm.getRemarks() + " this is remarks");
        return claimExpenseDto.updateClaimApproval(updateClaimApprovalForm);
    }

    @GetMapping("claim/get-all/{eventId}")
    public List<ClaimPojo> getEventClaims(@PathVariable("eventId") Long eventId) throws CommonApiException{
        return claimExpenseDto.getEventClaims(eventId);
    }

    @GetMapping("claim/get-all-expense/{claimId}")
    public List<ExpensePojo> getClaimExpenses(@PathVariable("claimId") Long claimId) throws CommonApiException {
        return claimExpenseDto.getClaimExpense(claimId);
    }

    @GetMapping("claim/get-all-approval/{claimId}")
    public List<ClaimApprovalPojo> getAllClaimApprovals(@PathVariable("claimId") Long claimId) throws CommonApiException {
        return claimExpenseDto.getAllClaimApprovals(claimId);
    }

    @GetMapping("claim/get-all-pending-approval")
    public List<ClaimApprovalPojo> getAllPendingClaimApproval() throws CommonApiException {
        return claimExpenseDto.getAllPendingClaimsApprovalForUser();
    }

    @GetMapping("claim/get/{claimId}")
    public ClaimPojo getClaimById(@PathVariable("claimId") Long claimId) throws CommonApiException {
        return claimExpenseDto.getClaimById(claimId);
    }

    @GetMapping("expense/download-file/{expenseId}")
    public byte[] downloadFile(@PathVariable("expenseId") Long expenseId) throws CommonApiException {
        System.out.println("We are in the download");
        return claimExpenseDto.downloadExpenseAttachment(expenseId);
    }
}
