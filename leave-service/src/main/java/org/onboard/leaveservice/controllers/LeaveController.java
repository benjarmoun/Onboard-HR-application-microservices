package org.onboard.leaveservice.controllers;

import lombok.AllArgsConstructor;
import org.onboard.leaveservice.dto.LeaveDaysDto;
import org.onboard.leaveservice.dto.LeaveDto;
import org.onboard.leaveservice.dto.SoldeDto;
import org.onboard.leaveservice.entites.Leave;
import org.onboard.leaveservice.exceptions.LeaveIdNotFoundException;
import org.onboard.leaveservice.exceptions.MissingFieldsException;
import org.onboard.leaveservice.models.Employee;
import org.onboard.leaveservice.repositories.LeaveRepository;
import org.onboard.leaveservice.services.EmployeeRestClientService;
import org.onboard.leaveservice.services.LeaveService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class LeaveController {
    private LeaveRepository leaveRepository;
    private LeaveService leaveService;
    private EmployeeRestClientService employeeRestClientService;

    @GetMapping("fullLeave/{id}")
    public Leave getLeave(@PathVariable Long id){
        Leave leave = leaveRepository.findById(id).get();
        Employee employee = employeeRestClientService.employeeById(leave.getId());
        leave.setEmployee(employee);

        return leave;
    }

    @PostMapping("requestLeave")
    public ResponseEntity<String> addLeaveRequest(@RequestBody LeaveDto leaveDto) throws MissingFieldsException {
        if(leaveService.createLeaveRequest(leaveDto) != null){
            return ResponseEntity.status(200).body("Leave requested successfully");
        }
        return ResponseEntity.status(400).body("An error has occured");
    }

    @GetMapping("Leave/{id}")
    public Leave getLeaveById(@PathVariable Long id){
        Leave leave = leaveService.getLeaveById(id);
        return leave;
    }

    @GetMapping("employeeLeave/{id}")
    public List<Leave> getLeavesByEmpId(@PathVariable Long id){
        List<Leave> leave = leaveService.getLeaveByEmpId(id);
        return leave;
    }
    @GetMapping("allLeave")
    public List<Leave> getAllLeave(){
        List<Leave> leave = leaveService.allLeaves();
        leave = leave.stream()
                .sorted(Comparator.comparing(Leave::getEndDate))
                .collect(Collectors.toList());
        return leave;
    }
    @GetMapping("pendingLeaveRequests")
    public List<Leave> getPendingLeave(){
        List<Leave> leave = leaveService.getPendingLeaves();
        leave = leave.stream()
                .filter(leave1 -> leave1.getStartDate().compareTo(new Date()) > 0)
                .sorted(Comparator.comparing(Leave::getEndDate))
                .collect(Collectors.toList());
        return leave;
    }

    @GetMapping("confirmLeave/{id}")
    public ResponseEntity<String> confirmLeaveById(@PathVariable Long id) throws LeaveIdNotFoundException {
        leaveService.confirmLeave(id);
        return ResponseEntity.status(200).body("Leave request confirmed successfully");
    }

    @GetMapping("rejectLeave/{id}")
    public ResponseEntity<String> rejectLeaveById(@PathVariable Long id) throws LeaveIdNotFoundException {
        leaveService.rejectLeave(id);
        return ResponseEntity.status(200).body("Leave request rejected successfully");
    }

    @DeleteMapping("deleteLeave/{id}")
    public ResponseEntity<String> deleteLeaveById(@PathVariable Long id) throws LeaveIdNotFoundException {
        leaveService.deleteLeave(id);
        return ResponseEntity.status(200).body("Leave deleted successfully");
    }

    @GetMapping("upcomingLeaves")
    public List<Leave> upcomingLeaves(){
        List<Leave> filteredLeaves = leaveService.comingLeaves();

        return filteredLeaves;
    }

    @GetMapping("leaveSold/{id}")
    public SoldeDto leaveSolde(@PathVariable Long id) {
        SoldeDto soldeDto =  leaveService.leaveSoldeByEmpId(id);
        return soldeDto;
    }

    @GetMapping("usedLeaveDays/{id}")
    public LeaveDaysDto usedLeaveDaysByEmpId(@PathVariable Long id) {
        Long leaveDays =  leaveService.usedLeavedaysByEmpId(id);
        LeaveDaysDto leaveDaysDto = new LeaveDaysDto();
        leaveDaysDto.setLeaveDays(leaveDays);
        return leaveDaysDto;
    }

}
