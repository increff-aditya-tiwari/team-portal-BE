package com.increff.teamer.pojo;

import com.increff.teamer.model.constant.RequestCategory;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.constant.RequestType;
import jakarta.persistence.*;

@Entity
@Table(name = "request_details",uniqueConstraints = @UniqueConstraint(name = "unique_request",columnNames = {"requestType","requestId","requestFor","requestBy","requestStatus","requestCategory"}))
public class RequestDetailPojo extends AbstractVersionedPojo{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestType requestType;
    @Column(nullable = false)
    private Long requestId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestCategory requestCategory;
    @Column(nullable = false)
    private Long requestFor;
    @Column(nullable = false)
    private Long requestBy;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;


    public RequestCategory getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(RequestCategory requestCategory) {
        this.requestCategory = requestCategory;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestFor() {
        return requestFor;
    }

    public void setRequestFor(Long requestFor) {
        this.requestFor = requestFor;
    }

    public Long getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(Long requestBy) {
        this.requestBy = requestBy;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
    public RequestDetailPojo(){

    }
    public RequestDetailPojo(RequestType requestType, Long requestId, RequestCategory requestCategory, Long requestFor, Long requestBy, RequestStatus requestStatus) {
        this.requestType = requestType;
        this.requestId = requestId;
        this.requestCategory= requestCategory;
        this.requestFor = requestFor;
        this.requestBy = requestBy;
        this.requestStatus = requestStatus;
    }
}
