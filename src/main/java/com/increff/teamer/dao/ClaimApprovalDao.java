package com.increff.teamer.dao;

import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.pojo.ClaimApprovalPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimApprovalDao extends JpaRepository<ClaimApprovalPojo,Long> {
//    public ClaimApprovalPojo findById(Long claimApprovalId);
    public List<ClaimApprovalPojo> findAllByApprovalRequiredByAndApprovalStatus(Long approvalRequiredBy, RequestStatus approvalStatus);
    public List<ClaimApprovalPojo> findAllByClaimId(Long claimId);
}
