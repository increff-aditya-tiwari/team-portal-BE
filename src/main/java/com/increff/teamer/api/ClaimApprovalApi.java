package com.increff.teamer.api;

import com.increff.teamer.dao.ClaimApprovalDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.form.UpdateClaimApprovalForm;
import com.increff.teamer.pojo.ClaimApprovalPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimApprovalApi {
    @Autowired
    ClaimApprovalDao claimApprovalDao;

    public void setClaimApproval(ClaimApprovalPojo claimApprovalPojo) throws CommonApiException{
        claimApprovalDao.save(claimApprovalPojo);
    }


    public ClaimApprovalPojo isValidClaimApproval(Long id) throws CommonApiException{
        Optional<ClaimApprovalPojo> claimApprovalPojoOptional = claimApprovalDao.findById(id);
        if(claimApprovalPojoOptional.isEmpty()){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Claim Approval is Invalid");
        }
        return claimApprovalPojoOptional.get();
    }

    public List<ClaimApprovalPojo> getAllClaimApprovals(Long claimId) throws CommonApiException{
        return claimApprovalDao.findAllByClaimId(claimId);
    }

    public List<ClaimApprovalPojo> getAllPendingClaimsApprovalForUser(Long userId) throws CommonApiException{
        return claimApprovalDao.findAllByApprovalRequiredByAndApprovalStatus(userId,RequestStatus.PENDING);

    }
}
