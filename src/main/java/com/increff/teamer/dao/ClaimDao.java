package com.increff.teamer.dao;

import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.pojo.ClaimPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimDao extends JpaRepository<ClaimPojo,Long> {
    public ClaimPojo findByEventIdAndClaimStatus(Long eventId, RequestStatus claimStatus);
    public List<ClaimPojo> findAllByEventId(Long eventId);

    public ClaimPojo findByClaimId(Long claimId);
}
