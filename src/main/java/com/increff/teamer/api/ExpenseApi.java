package com.increff.teamer.api;

import com.increff.teamer.dao.NewExpenseDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.pojo.ExpensePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExpenseApi {
    @Autowired
    private NewExpenseDao newExpenseDao;

    public ExpensePojo setExpense(ExpensePojo expensePojo) throws CommonApiException{
        return newExpenseDao.save(expensePojo);
    }
    public void removeExpense(ExpensePojo expensePojo) throws CommonApiException{
        newExpenseDao.delete(expensePojo);
    }
    public ExpensePojo isValidExpense(Long expenseId) throws CommonApiException{
        ExpensePojo expensePojo = newExpenseDao.findByExpenseId(expenseId);
        if(expensePojo == null){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Expense with expense Id "+expenseId+" is not present");
        }
        return expensePojo;
    }

    public ExpensePojo updateExpense(ExpensePojo newExpensePojo, ExpensePojo existingExpensePojo) throws CommonApiException{
        existingExpensePojo.setExpenseAmount(newExpensePojo.getExpenseAmount());
        existingExpensePojo.setDescription(newExpensePojo.getDescription());
        existingExpensePojo.setInvoiceDate(newExpensePojo.getInvoiceDate());
        existingExpensePojo.setInvoiceNo(newExpensePojo.getInvoiceNo());
        return newExpenseDao.save(existingExpensePojo);
    }

    public List<ExpensePojo> getClaimExpenses(Long claimId) throws CommonApiException{
        return newExpenseDao.findAllByClaimId(claimId);
    }

    public ExpensePojo getExpenseAttachment(Long expenseId) throws CommonApiException{
        return newExpenseDao.findByExpenseId(expenseId);
    }
}
