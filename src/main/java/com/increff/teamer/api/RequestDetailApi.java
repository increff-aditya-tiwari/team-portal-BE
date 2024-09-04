package com.increff.teamer.api;

import com.increff.teamer.dao.RequestDetailDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.RequestCategory;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.constant.RequestType;
import com.increff.teamer.pojo.RequestDetailPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestDetailApi {

    @Autowired
    RequestDetailDao requestDetailDao;

    public void saveAll(List<RequestDetailPojo> requestDetailPojoList) throws CommonApiException{
        requestDetailDao.saveAll(requestDetailPojoList);
    }

    public List<RequestDetailPojo> getAllOpenRequestsForTeam(Long requestId) throws CommonApiException{
        return requestDetailDao.findAllByRequestIdAndRequestTypeAndRequestStatusAndRequestCategory(requestId, RequestType.TEAM, RequestStatus.PENDING, RequestCategory.REQUEST);
    }

    public List<RequestDetailPojo> getAllOpenRequestsForEvent(Long requestId) throws CommonApiException{
        return requestDetailDao.findAllByRequestIdAndRequestTypeAndRequestStatusAndRequestCategory(requestId, RequestType.EVENT, RequestStatus.PENDING, RequestCategory.REQUEST);
    }
    public List<RequestDetailPojo> getAllOpenInvitesForTeam(Long requestId,Long userId) throws CommonApiException{
        return requestDetailDao.findAllByRequestIdAndRequestForAndRequestCategory(requestId,userId,RequestCategory.INVITE);
//        return requestDetailDao.findAllByRequestIdAndRequestTypeAndRequestStatusAndRequestCategory(requestId,RequestType.TEAM,RequestStatus.PENDING,RequestCategory.INVITE);
    }

    public RequestDetailPojo isValidRequest(Long requestDetailId) throws CommonApiException{
        Optional<RequestDetailPojo> requestDetailPojoOptional = requestDetailDao.findById(requestDetailId);
        if(requestDetailPojoOptional.isEmpty()){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Request is Expired Or Not Exist");
        }
        return requestDetailPojoOptional.get();
    }

    public Boolean validateRequestForRequestType(RequestDetailPojo requestDetailPojo){
        RequestDetailPojo existingPendingRequest = requestDetailDao.findByRequestIdAndRequestTypeAndRequestForAndRequestByAndRequestStatus(requestDetailPojo.getRequestId(), requestDetailPojo.getRequestType(),requestDetailPojo.getRequestFor(),requestDetailPojo.getRequestBy(), RequestStatus.PENDING);
        RequestDetailPojo existingAcceptedRequest = requestDetailDao.findByRequestIdAndRequestTypeAndRequestForAndRequestByAndRequestStatus(requestDetailPojo.getRequestId(), requestDetailPojo.getRequestType(),requestDetailPojo.getRequestFor(),requestDetailPojo.getRequestBy(), RequestStatus.ACCEPTED);
        return existingPendingRequest == null && existingAcceptedRequest == null;
    }
}
