package com.increff.teamer.pojo;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "new_expense_details", uniqueConstraints = @UniqueConstraint(name="unique_expense",columnNames = {"claimId","invoiceNo"}))
public class NewExpensePojo extends AbstractVersionedPojo{
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
    private byte[]  attachment;

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

    public byte[] getAttachment() {
        return attachment;
    }

    public NewExpensePojo(){

    }
    public NewExpensePojo(Long claimId, Long expenseAmount, String invoiceNo, String invoiceDate, String description, byte[] attachment) {
        this.claimId = claimId;
        this.expenseAmount = expenseAmount;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.description = description;
        this.attachment = attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
