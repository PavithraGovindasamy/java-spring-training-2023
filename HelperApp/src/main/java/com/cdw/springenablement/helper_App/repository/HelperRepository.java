package com.cdw.springenablement.helper_App.repository;

import com.cdw.springenablement.helper_App.entity.Helper;
import com.cdw.springenablement.helper_App.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelperRepository extends JpaRepository<Helper, Long>{

    List<Helper> findByTimeSlotsNotContains(TimeSlot slot);

    List<Helper> findByTimeSlotsContains(TimeSlot slot);
}
