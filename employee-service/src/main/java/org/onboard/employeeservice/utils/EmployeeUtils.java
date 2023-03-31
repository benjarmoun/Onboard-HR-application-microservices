package org.onboard.employeeservice.utils;

import org.onboard.employeeservice.dto.EmployeeInputDto;

public class EmployeeUtils {
    public static boolean checkEmployeeInputDtoFields(EmployeeInputDto employeeInputDto) {
        return employeeInputDto.getAddress() == null || employeeInputDto.getAddress().isEmpty()
            || employeeInputDto.getFname() == null || employeeInputDto.getFname().isEmpty()
            || employeeInputDto.getLname() == null || employeeInputDto.getLname().isEmpty()
            || employeeInputDto.getEmail() == null || employeeInputDto.getEmail().isEmpty()
            || employeeInputDto.getPassword() == null || employeeInputDto.getPassword().isEmpty()
            || employeeInputDto.getPhone() == null || employeeInputDto.getPhone().isEmpty()
            || employeeInputDto.getRole() == null || employeeInputDto.getRole().isEmpty();
    }

}
