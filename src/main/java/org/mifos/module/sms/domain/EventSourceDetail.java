package org.mifos.module.sms.domain;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "event_sourcing_details")
public class EventSourceDetail {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "entity_description")
    private String entitydescription;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntitydescription() {
        return this.entitydescription;
    }

    public void setEntitydescription(String entitydescription) {
        this.entitydescription = entitydescription;
    }

    @Column(name = "entity")
    private String entity;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_mobile_no")
    private String entityMobileNo;

    @Column(name = "action")
    private String action;

    @Column(name = "payload")
    private String payload;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "last_modified_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedOn;

    public EventSourceDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntity_Mobile_No() {
        return entityMobileNo;
    }

    public void setEntity_Mobile_No(String entity_Mobile_No) {
        this.entityMobileNo = entity_Mobile_No;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

}
