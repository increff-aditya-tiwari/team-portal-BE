package com.increff.teamer.pojo;

import com.increff.teamer.model.constant.EventCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "event_category_approval_sequence")
public class EventCategoryApprovalSequencePojo{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;
    @Column(nullable = false)
    private Long approvalRequiredBy;
    @Column(nullable = false)
    private Long approvalStage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Long getApprovalRequiredBy() {
        return approvalRequiredBy;
    }

    public void setApprovalRequiredBy(Long approvalRequiredBy) {
        this.approvalRequiredBy = approvalRequiredBy;
    }

    public Long getApprovalStage() {
        return approvalStage;
    }

    public void setApprovalStage(Long approvalStage) {
        this.approvalStage = approvalStage;
    }
}
