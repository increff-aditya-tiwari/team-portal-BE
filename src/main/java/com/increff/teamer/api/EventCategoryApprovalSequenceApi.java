package com.increff.teamer.api;

import com.increff.teamer.dao.EventCategoryApprovalSequenceDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.EventCategory;
import com.increff.teamer.pojo.EventCategoryApprovalSequencePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventCategoryApprovalSequenceApi {
    @Autowired
    EventCategoryApprovalSequenceDao eventCategoryApprovalSequenceDao;

    public EventCategoryApprovalSequencePojo getApprovalDetail(EventCategory eventCategory,Long approvalStage) throws CommonApiException{
        return eventCategoryApprovalSequenceDao.findByEventCategoryAndApprovalStage(eventCategory,approvalStage);
    }
}
