package com.increff.teamer.model.data;

import com.increff.teamer.pojo.EventPojo;
import com.increff.teamer.pojo.RequestDetailPojo;

import java.util.List;

public class EventData {
    private EventPojo event;
    private List<RequestDetailPojo> requestList;
    private List<RequestDetailPojo> inviteList;

    public EventPojo getEvent() {
        return event;
    }

    public void setEvent(EventPojo eventPojo) {
        this.event = eventPojo;
    }

    public List<RequestDetailPojo> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<RequestDetailPojo> requestList) {
        this.requestList = requestList;
    }

    public List<RequestDetailPojo> getInviteList() {
        return inviteList;
    }

    public void setInviteList(List<RequestDetailPojo> inviteList) {
        this.inviteList = inviteList;
    }

    public EventData(EventPojo event, List<RequestDetailPojo> requestList, List<RequestDetailPojo> inviteList) {
        this.event = event;
        this.requestList = requestList;
        this.inviteList = inviteList;
    }
}
