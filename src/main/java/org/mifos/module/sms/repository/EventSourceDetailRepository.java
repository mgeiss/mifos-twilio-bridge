package org.mifos.module.sms.repository;

import java.util.ArrayList;
import org.mifos.module.sms.domain.EventSourceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSourceDetailRepository extends JpaRepository<EventSourceDetail, Long>,JpaSpecificationExecutor<EventSourceDetail> {

    public EventSourceDetail findByeventId(final Long eventId);

    @Query(" from EventSourceDetail esd where esd.entityId=:entity_idparam and esd.entityMobileNo=:mobilenoparam and esd.entityName=:entitynameparam and esd.processed=:processedparam")
    public ArrayList<EventSourceDetail> findByEntityIdandMobileNumberandProcessed(@Param("entity_idparam") String entityId,
            @Param("mobilenoparam") String entityMobileNo,
            @Param("entitynameparam")String entityName,
            @Param("processedparam") Boolean processed);

}
