package com.increff.teamer.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "expense_details", uniqueConstraints = @UniqueConstraint(name="unique_expense",columnNames = {"claimId","invoiceNo"}))
public class ExpensePojo extends AbstractVersionedPojo{
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;
    @Column(nullable = false)
    private Long claimId;
    @Column(nullable = false)
    private Long expenseAmount;
    @Column(nullable = false)
    private String invoiceNo;
    @Column(nullable = true)
    private String invoiceDate;
    @Column(nullable = false,length = 5000)
    private String description;
    @Lob
    @Column(nullable = false,length = 1000000)
    private byte[] attachmentDetail;

    @NotNull
    @NotEmpty
    private String attachmentFileName;

    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

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

    public byte[] getAttachmentDetail() {
        return attachmentDetail;
    }

    public ExpensePojo(){

    }
    public ExpensePojo(Long claimId, Long expenseAmount, String invoiceNo, String invoiceDate, String description, byte[] attachmentDetail) {
        this.claimId = claimId;
        this.expenseAmount = expenseAmount;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.description = description;
        this.attachmentDetail = attachmentDetail;
    }

    public void setAttachmentDetail(byte[] attachmentDetail) {
        this.attachmentDetail = attachmentDetail;
    }
}
