package com.increff.teamer.pojo;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "expense_details", uniqueConstraints = @UniqueConstraint(name="unique_expense",columnNames = {"claimId","invoiceNo"}))
public class ExpenseDetailsPojo extends AbstractVersionedPojo{
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
    private ZonedDateTime invoiceDate;
    @Column(nullable = false,length = 5000)
    private String description;
    @Column(nullable = false,length = 5000)
    private String  attachment;

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

    public String getAttachment() {
        return attachment;
    }

    public ExpenseDetailsPojo(){

    }
    public ExpenseDetailsPojo(Long claimId, Long expenseAmount, String invoiceNo, ZonedDateTime invoiceDate, String description, String attachment) {
        this.claimId = claimId;
        this.expenseAmount = expenseAmount;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.description = description;
        this.attachment = attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
