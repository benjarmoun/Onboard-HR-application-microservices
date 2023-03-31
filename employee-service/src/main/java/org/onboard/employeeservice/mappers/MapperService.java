package org.onboard.employeeservice.mappers;

import org.onboard.employeeservice.dto.ContractDto;
import org.onboard.employeeservice.dto.EmployeeInputDto;
import org.onboard.employeeservice.dto.EmployeeOutputDto;
import org.onboard.employeeservice.entities.Contract;
import org.onboard.employeeservice.entities.Employee;
import org.onboard.employeeservice.repositories.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    private final EmployeeRepository employeeRepository;

    public MapperService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeOutputDto fromEmployee(Employee employee){
        EmployeeOutputDto employeeOutputDto = new EmployeeOutputDto();
        BeanUtils.copyProperties(employee, employeeOutputDto);
        return employeeOutputDto;
    }
    public Employee inputDtoToEmployee(EmployeeInputDto employeeInputDto){
        Employee employee = Employee.builder()
                .fname(employeeInputDto.getFname())
                .lname(employeeInputDto.getLname())
                .email(employeeInputDto.getEmail())
                .password(BCrypt.hashpw(employeeInputDto.getPassword(), BCrypt.gensalt(10)))
                .phone(employeeInputDto.getPhone())
                .address(employeeInputDto.getAddress())
                .role(employeeInputDto.getRole())
                .contract(employeeInputDto.getContract())
                .build();
//        BeanUtils.copyProperties(employee, employeeInputDto);
        return employee;
    }

    public ContractDto fromContract(Contract contract){
        ContractDto contractDto = new ContractDto();
        BeanUtils.copyProperties(contract, contractDto);
        return contractDto;
    }

    public Contract contractDtoToContract(ContractDto contractDto){
        Contract contract = Contract.builder()
                .fonction(contractDto.getFonction())
                .type(contractDto.getType())
                .salary(contractDto.getSalary())
                .startDate(contractDto.getStartDate())
                .endDate(contractDto.getEndtDate())
                .build();
        return contract;
    }








}
