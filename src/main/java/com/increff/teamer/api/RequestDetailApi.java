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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RequestDetailApi {

    @Autowired
    private RequestDetailDao requestDetailDao;

    public void saveAll(List<RequestDetailPojo> requestDetailPojoList) throws CommonApiException{
        requestDetailDao.saveAll(requestDetailPojoList);
    }

    public List<RequestDetailPojo> getAllOpenRequestsForTeam(Long requestId) throws CommonApiException{
        return requestDetailDao.findAllByRequestIdAndRequestTypeAndRequestStatusAndRequestCategory(requestId, RequestType.TEAM, RequestStatus.PENDING, RequestCategory.REQUEST);
    }

    public List<RequestDetailPojo> getAllOpenRequestsForEvent(Long requestId) throws CommonApiException{
        return requestDetailDao.findAllByRequestIdAndRequestTypeAndRequestStatusAndRequestCategory(requestId, RequestType.EVENT, RequestStatus.PENDING, RequestCategory.REQUEST);
    }
    public List<RequestDetailPojo> getAllOpenInvitesForTeam(Long requestId,Long requestFor,Long requestBy) throws CommonApiException{
        RequestDetailPojo requestDetailPojo = requestDetailDao.findByRequestIdAndRequestTypeAndRequestForAndRequestByAndRequestStatusAndRequestCategory(requestId,RequestType.TEAM,requestFor,requestBy,RequestStatus.PENDING,RequestCategory.INVITE);
        return requestDetailPojo == null ? new ArrayList<>() : Collections.singletonList(requestDetailPojo);
    }
    public List<RequestDetailPojo> getAllOpenInvitesForEvent(Long requestId,Long requestFor,Long requestBy) throws CommonApiException{
        RequestDetailPojo requestDetailPojo = requestDetailDao.findByRequestIdAndRequestTypeAndRequestForAndRequestByAndRequestStatusAndRequestCategory(requestId,RequestType.EVENT,requestFor,requestBy,RequestStatus.PENDING,RequestCategory.INVITE);
        return requestDetailPojo == null ? new ArrayList<>() : Collections.singletonList(requestDetailPojo);
    }

    public RequestDetailPojo isValidRequest(Long requestDetailId) throws CommonApiException{
        Optional<RequestDetailPojo> requestDetailPojoOptional = requestDetailDao.findById(requestDetailId);
        if(requestDetailPojoOptional.isEmpty()){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Request is Expired Or Not Exist");
        }
        return requestDetailPojoOptional.get();
    }

    public RequestDetailPojo validateRequestToAddRequest(RequestDetailPojo requestDetailPojo) throws CommonApiException {

//        if (existingRequestDetailPojo != null && existingRequestDetailPojo.getRequestStatus() == RequestStatus.PENDING) {
//            throw new CommonApiException(HttpStatus.BAD_REQUEST, "Already Requested or Invited to join this team");
//        }
        return requestDetailDao.findByRequestIdAndRequestTypeAndRequestForAndRequestBy(
                requestDetailPojo.getRequestId(), requestDetailPojo.getRequestType(), requestDetailPojo.getRequestFor(), requestDetailPojo.getRequestBy());
    }

    public Boolean validateRequestForRequestType(RequestDetailPojo requestDetailPojo){
        RequestCategory requestCategory = requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST ? RequestCategory.INVITE : RequestCategory.REQUEST;
        RequestDetailPojo existingPendingRequestInvite = requestDetailDao.findByRequestIdAndRequestTypeAndRequestForAndRequestByAndRequestStatusAndRequestCategory(requestDetailPojo.getRequestId(), requestDetailPojo.getRequestType(),requestDetailPojo.getRequestBy(),requestDetailPojo.getRequestFor(), RequestStatus.PENDING,requestCategory);
//        RequestDetailPojo existingAcceptedRequest = requestDetailDao.findByRequestIdAndRequestTypeAndRequestForAndRequestByAndRequestStatus(requestDetailPojo.getRequestId(), requestDetailPojo.getRequestType(),requestDetailPojo.getRequestFor(),requestDetailPojo.getRequestBy(), RequestStatus.ACCEPTED);
//        return existingPendingRequest == null && existingAcceptedRequest == null;
        return existingPendingRequestInvite == null;
    }
}

