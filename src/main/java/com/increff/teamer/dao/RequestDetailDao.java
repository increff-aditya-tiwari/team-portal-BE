package com.increff.teamer.dao;

import com.increff.teamer.model.constant.RequestCategory;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.constant.RequestType;
import com.increff.teamer.pojo.RequestDetailPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestDetailDao extends JpaRepository<RequestDetailPojo,Long> {
    public RequestDetailPojo findByRequestId(Long requestId);
//    public RequestDetailPojo findById(Long id);
    public List<RequestDetailPojo> findAllByRequestIdAndRequestForAndRequestCategory(Long requestId,Long requestFor,RequestCategory requestCategory);
    public List<RequestDetailPojo> findAllByRequestIdAndRequestTypeAndRequestStatus(Long requestId, RequestType requestType, RequestStatus requestStatus);
    public List<RequestDetailPojo> findAllByRequestIdAndRequestTypeAndRequestStatusAndRequestCategory(Long requestId, RequestType requestType, RequestStatus requestStatus, RequestCategory requestCategory);
    public RequestDetailPojo findByRequestIdAndRequestTypeAndRequestForAndRequestByAndRequestStatus(Long requestId,RequestType requestType,Long requestFor,Long requestBy,RequestStatus requestStatus);
//    public RequestDetailPojo findById(Long requestDetailId);
    public RequestDetailPojo findByRequestIdAndRequestTypeAndRequestForAndRequestBy(Long requestId,RequestType requestType,Long requestFor,Long requestBy);
    public RequestDetailPojo findByRequestIdAndRequestTypeAndRequestForAndRequestByAndRequestStatusAndRequestCategory(Long requestId,RequestType requestType,Long requestFor,Long requestBy,RequestStatus requestStatus,RequestCategory requestCategory);
}
