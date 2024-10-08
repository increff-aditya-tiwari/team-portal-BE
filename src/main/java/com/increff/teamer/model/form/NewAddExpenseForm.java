package com.increff.teamer.model.form;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public class NewAddExpenseForm {
    @NotNull
    @Positive
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
    private MultipartFile attachmentDetail;
    @NotNull
    private Long claimId;

    public Long getExpenseAmount() {
        return expenseAmount;
    }

    @Nullable
    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
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

    public MultipartFile getAttachmentDetail() {
        return attachmentDetail;
    }

    public void setAttachmentDetail(MultipartFile attachmentDetail) {
        this.attachmentDetail = attachmentDetail;
    }
}
