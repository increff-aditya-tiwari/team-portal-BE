package com.increff.teamer.api;

import com.increff.teamer.dao.ClaimDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.pojo.ClaimPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimApi {
    @Autowired
    private ClaimDao claimDao;
    public void validateClaimToAdd(Long eventId)throws CommonApiException {
        ClaimPojo pendingClaimPojo = claimDao.findByEventIdAndClaimStatus(eventId, RequestStatus.PENDING);
        ClaimPojo approvedClaimPojo = claimDao.findByEventIdAndClaimStatus(eventId,RequestStatus.APPROVED);
        if(pendingClaimPojo != null ){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already have one Active Claim");
        } else if (approvedClaimPojo != null) {
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Already have one Approved Claim");
        }
    }
    public ClaimPojo saveClaim(ClaimPojo claimPojo) throws CommonApiException{
        return claimDao.save(claimPojo);
    }

    public ClaimPojo isValidClaim(Long claimId) throws CommonApiException{
        ClaimPojo claimPojo = claimDao.findByClaimId(claimId);
        if(claimPojo == null){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Claim with claim id "+ claimId + " not exist");
        }
        return claimPojo;
    }

    public ClaimPojo validClaimToUpdateExpense(Long claimId) throws CommonApiException{
        ClaimPojo claimPojo = isValidClaim(claimId);
        if(claimPojo.getClaimStatus() != RequestStatus.PENDING){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Your Claim is not Active");
        }
        return claimPojo;
    }

    public ClaimPojo updateClaimStatus(Long claimId,RequestStatus newClaimStatus) throws CommonApiException{
        ClaimPojo claimPojo = isValidClaim(claimId);
        claimPojo.setClaimStatus(newClaimStatus);
        return saveClaim(claimPojo);
    }

    public List<ClaimPojo> getEventClaims(Long eventId) throws CommonApiException{
        return claimDao.findAllByEventId(eventId);
    }
}
