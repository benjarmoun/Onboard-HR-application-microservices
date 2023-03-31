package org.onboard.leaveservice.controllers;

import lombok.AllArgsConstructor;
import org.onboard.leaveservice.entites.Leave;
import org.onboard.leaveservice.models.Employee;
import org.onboard.leaveservice.repositories.LeaveRepository;
import org.onboard.leaveservice.services.EmployeeRestClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LeaveController {
    private LeaveRepository leaveRepository;
    private EmployeeRestClientService employeeRestClientService;

    @GetMapping("fullLeave/{id}")
    public Leave getLeave(@PathVariable Long id){
        Leave leave = leaveRepository.findById(id).get();
        Employee employee = employeeRestClientService.employeeById(leave.getId());
        leave.setEmployee(employee);

        return leave;
    }
    @GetMapping("fullLeave")
    public List<Leave> getAllLeave(){
        List<Leave> leave =  leaveRepository.findAll();
        leave.forEach(leave1 ->{
                Employee employee = employeeRestClientService.employeeById(leave1.getId());
                leave1.setEmployee(employee);
                });


        return leave;
    }

}
