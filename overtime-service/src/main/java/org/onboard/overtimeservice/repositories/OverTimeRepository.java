package org.onboard.overtimeservice.repositories;

import org.onboard.overtimeservice.entities.OverTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OverTimeRepository extends JpaRepository<OverTime, Long> {
}
