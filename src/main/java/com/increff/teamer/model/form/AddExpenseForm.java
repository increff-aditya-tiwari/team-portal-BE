package com.increff.teamer.model.form;

import jakarta.annotation.Nullable;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

public class AddExpenseForm {
    @NotNull
    private Long expenseAmount;
    @NotNull
    @NotEmpty
    private String invoiceNo;
    @NotNull
    @NotEmpty
    private ZonedDateTime invoiceDate;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String attachment;
    @NotNull
    private Long claimId;

    public Long getExpenseAmount() {
        return expenseAmount;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId( Long claimId) {
        this.claimId = claimId;
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

    public ZonedDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(ZonedDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String  getAttachment() {
        return attachment;
    }

    public void setAttachment(String  attachment) {
        this.attachment = attachment;
    }
}
