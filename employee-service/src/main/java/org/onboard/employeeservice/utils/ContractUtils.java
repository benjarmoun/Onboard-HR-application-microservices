package org.onboard.employeeservice.utils;

import org.onboard.employeeservice.dto.ContractDto;
import org.onboard.employeeservice.dto.EmployeeInputDto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContractUtils {
    public static boolean checkContractInputDtoFields(ContractDto contractInputDto) {
        return contractInputDto.getType() == null || contractInputDto.getType().isEmpty()
                || contractInputDto.getSalary() == null
                || contractInputDto.getFonction() == null || contractInputDto.getFonction().isEmpty()
                || contractInputDto.getStartDate() == null || contractInputDto.getStartDate().isBefore(LocalDate.now());
    }
}
