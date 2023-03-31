package org.onboard.employeeservice.services;

import lombok.AllArgsConstructor;
import org.onboard.employeeservice.dto.ContractDto;
import org.onboard.employeeservice.entities.Contract;
import org.onboard.employeeservice.entities.Employee;
import org.onboard.employeeservice.exeptions.ContractIdNotFoundException;
import org.onboard.employeeservice.exeptions.EmployeeIdNotFoundException;
import org.onboard.employeeservice.exeptions.MissingFieldsException;
import org.onboard.employeeservice.mappers.MapperService;
import org.onboard.employeeservice.repositories.ContractRepository;
import org.onboard.employeeservice.utils.ContractUtils;
import org.onboard.employeeservice.utils.EmployeeUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ContractServiceImp implements ContractService{
    private MapperService mapperService;
    final ContractRepository contractRepository;

    @Override
    public List<Contract> allContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contract createContract(ContractDto contractDto) throws MissingFieldsException {
        if(ContractUtils.checkContractInputDtoFields(contractDto))
            throw new MissingFieldsException();
        Contract contract = this.mapperService.contractDtoToContract(contractDto);
        return contractRepository.save(contract);
    }

    @Override
    public void deleteContract(Long id) throws ContractIdNotFoundException {
        Contract contract = contractRepository.findById(id).orElseThrow(()-> new ContractIdNotFoundException(id));
        contractRepository.delete(contract);
    }

    @Override
    public ContractDto updateContract(Long id, ContractDto contractDto) throws MissingFieldsException, ContractIdNotFoundException {
        if (ContractUtils.checkContractInputDtoFields(contractDto))
            throw new MissingFieldsException();
        contractRepository.findById(id).orElseThrow(()-> new ContractIdNotFoundException(id));
        Contract contract = mapperService.contractDtoToContract(contractDto);
        contract.setId(id);
        return mapperService.fromContract(contractRepository.save(contract));
    }


    @Override
    public Contract getContractById(Long id) {
        Optional contract = contractRepository.findById(id);
        return contract.isPresent() ? (Contract) contract.get() : null;
    }
}
