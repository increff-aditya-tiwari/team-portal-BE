package com.increff.teamer.model.data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ExpenseData {
    @NotNull
    private Long expenseId;
    @NotNull
    private Long expenseAmount;
    @NotNull
    @NotEmpty
    private String invoiceNo;
    @NotNull
    @NotEmpty
    private String  invoiceDate;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String attachmentName;
    @NotNull
    private Long claimId;

    public Long getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Long expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public ExpenseData(Long expenseId, Long expenseAmount, String invoiceNo, String invoiceDate, String description, String attachmentName, Long claimId) {
        this.expenseId = expenseId;
        this.expenseAmount = expenseAmount;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.description = description;
        this.attachmentName = attachmentName;
        this.claimId = claimId;
    }
}
