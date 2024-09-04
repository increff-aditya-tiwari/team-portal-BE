package com.increff.teamer.dao;

import com.increff.teamer.pojo.NewExpensePojo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewExpenseDao extends JpaRepository<NewExpensePojo,Long> {
}
