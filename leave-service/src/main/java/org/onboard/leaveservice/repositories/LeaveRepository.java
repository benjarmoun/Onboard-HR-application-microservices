package org.onboard.leaveservice.repositories;

import org.onboard.leaveservice.entites.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LeaveRepository extends JpaRepository<Leave, Long> {

}
