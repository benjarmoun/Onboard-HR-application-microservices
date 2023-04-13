package org.onboard.employeeservice.controllers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.onboard.employeeservice.dto.LeaveDto;
import org.onboard.employeeservice.models.Leave;
import org.onboard.employeeservice.services.ContractServiceImp;
import org.onboard.employeeservice.services.EmployeeService;
import org.onboard.employeeservice.services.LeaveRestClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RefreshScope
@AllArgsConstructor
@RequestMapping("employee")
public class EmployeeController {

    EmployeeService employeeService;
    LeaveRestClientService leaveRestClientService;

    @GetMapping("employee") //to employee
    public ResponseEntity<Object> getAccountByEmail(HttpServletRequest httpSerReq){
        String email = employeeService.getEmail(httpSerReq);
        return ResponseEntity.ok(employeeService.getEmployeeByEmail(email));
    }


    @PostMapping("requestLeave")  //employee
    public ResponseEntity<String> createLeaveRequest(@RequestBody LeaveDto leaveDto, HttpServletRequest httpSerReq){
        leaveDto.setEmployeeId(employeeService.getEmpId(httpSerReq));
        if(leaveRestClientService.addLeaveRequest(leaveDto).getStatusCode().value() == 200){
            return ResponseEntity.status(200).body("Leave requested successfully");
        }else
            return ResponseEntity.status(400).body("An error has occured");
    }

    @GetMapping("LeaveSoldeEmployee")  //to employee
    public ResponseEntity<Object> leaveSoldeEmployee(HttpServletRequest httpSerReq){
        Long id = employeeService.getEmpId(httpSerReq);
        return ResponseEntity.ok(leaveRestClientService.leaveSolde(id));
    }

    @GetMapping("usedLeaveDays")  //to employee
    public ResponseEntity<Object> usedLeaveDaysByEmp(HttpServletRequest httpSerReq){
        Long id = employeeService.getEmpId(httpSerReq);
        return ResponseEntity.ok(leaveRestClientService.usedLeaveDaysByEmpId(id));
    }

    @GetMapping("employeeUpcomingLeaves")
    public ResponseEntity<Object> upcomingLeavesByEmp(HttpServletRequest httpSerReq){
        Long id = employeeService.getEmpId(httpSerReq);
        List<Leave> leaves =  leaveRestClientService.getUpcomingLeavesByEmpId(id);
        leaves.stream()
                .forEach(leave -> leave.setEmployee(employeeService.getEmployeeById(leave.getEmployeeId())));
        return ResponseEntity.ok(leaves);
    }

    @GetMapping("LeavesByEmployee")  //to employee
    public ResponseEntity<Object> leavesByEmployee(HttpServletRequest httpSerReq){
        Long id = employeeService.getEmpId(httpSerReq);
        System.out.println("id"+id);
        List<Leave> leaves =  leaveRestClientService.getLeavesByEmpId(id);
        System.out.println(leaves);
        leaves.stream()
                .forEach(leave -> leave.setEmployee(employeeService.getEmployeeById(leave.getEmployeeId())));
        return ResponseEntity.ok(leaves);
    }

}
