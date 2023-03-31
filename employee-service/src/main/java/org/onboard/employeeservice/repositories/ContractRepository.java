package org.onboard.employeeservice.repositories;


import org.onboard.employeeservice.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ContractRepository extends JpaRepository<Contract ,Long> {
    @Override
    Contract getById(Long Long);
}
