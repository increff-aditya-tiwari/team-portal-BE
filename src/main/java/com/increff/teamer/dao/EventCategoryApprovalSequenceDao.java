package com.increff.teamer.dao;

import com.increff.teamer.model.constant.EventCategory;
import com.increff.teamer.pojo.EventCategoryApprovalSequencePojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCategoryApprovalSequenceDao extends JpaRepository<EventCategoryApprovalSequencePojo,Long> {
    public EventCategoryApprovalSequencePojo findByEventCategoryAndApprovalStage(EventCategory eventCategory,Long approvalStage);
}
