package com.increff.teamer.model.form;

import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

public class NewAddExpenseForm {
    private Long expenseAmount;
    private String invoiceNo;
    private String  invoiceDate;
    private String description;
    private MultipartFile attachment;
    @Nullable
    private Long claimId;

    public Long getExpenseAmount() {
        return expenseAmount;
    }

    @Nullable
    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(@Nullable Long claimId) {
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

    public MultipartFile  getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile  attachment) {
        this.attachment = attachment;
    }
}
