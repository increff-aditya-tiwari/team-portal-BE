package com.increff.teamer.dao;

import com.increff.teamer.pojo.ExpenseDetailsPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseDetailsDao extends JpaRepository<ExpenseDetailsPojo,Long> {
    public List<ExpenseDetailsPojo> findAllByClaimId(Long claimId);
}
