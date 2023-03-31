package org.onboard.employeeservice.services;

import org.onboard.employeeservice.dto.ContractDto;
import org.onboard.employeeservice.entities.Contract;
import org.onboard.employeeservice.exeptions.ContractIdNotFoundException;
import org.onboard.employeeservice.exeptions.MissingFieldsException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ContractService {
    List<Contract> allContracts();
    //    EmployeeOutputDto findEmployeeBySecureId(String id) throws EmployeeIdNotFoundException;
    Contract createContract(ContractDto ContractInputDto) throws MissingFieldsException;
    void deleteContract(Long id) throws ContractIdNotFoundException;
    ContractDto updateContract(Long id, ContractDto ContractInputDto) throws MissingFieldsException, ContractIdNotFoundException;


    Contract getContractById(Long id);

}
