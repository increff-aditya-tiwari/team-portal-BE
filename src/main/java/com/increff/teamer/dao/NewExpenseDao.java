package com.increff.teamer.dao;

import com.increff.teamer.pojo.ExpensePojo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewExpenseDao extends JpaRepository<ExpensePojo,Long> {
    public List<ExpensePojo> findAllByClaimId(Long claimId);
    public ExpensePojo findByExpenseId(Long expenseId);
    public ExpensePojo findByClaimIdAndInvoiceNo(Long claimId,String invoiceNo);
}
