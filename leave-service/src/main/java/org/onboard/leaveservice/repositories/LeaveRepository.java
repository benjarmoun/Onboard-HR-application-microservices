package org.onboard.leaveservice.repositories;

import org.onboard.leaveservice.entites.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    Optional<Leave> findById(Long id);

    List<Leave> findByEmployeeId(Long id);



}
