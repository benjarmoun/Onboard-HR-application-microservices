package org.onboard.employeeservice.services;

import org.onboard.employeeservice.dto.LeaveDaysDto;
import org.onboard.employeeservice.dto.LeaveDto;
import org.onboard.employeeservice.dto.SoldeDto;
import org.onboard.employeeservice.models.Leave;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "leave-service")
public interface LeaveRestClientService {
    @GetMapping("leave/{id}")
    public Leave getLeaveById(@PathVariable Long id);

    @GetMapping("leaves")
    public PagedModel<Leave> allLeaves();

    @GetMapping("allLeave")
    public List<Leave> getAllLeaves();

    @GetMapping("employeeLeave/{id}")
    public List<Leave> getLeavesByEmpId(@PathVariable Long id);

    @PostMapping("requestLeave")
    public ResponseEntity<String> addLeaveRequest(@RequestBody LeaveDto leaveDto);

    @GetMapping("pendingLeaveRequests")
    public List<Leave> getPendingLeave();

    @GetMapping("confirmLeave/{id}")
    public ResponseEntity<String> confirmLeaveById(@PathVariable Long id);

    @GetMapping("rejectLeave/{id}")
    public ResponseEntity<String> rejectLeaveById(@PathVariable Long id);

    @DeleteMapping("deleteLeave/{id}")
    public ResponseEntity<String> deleteLeaveById(@PathVariable Long id);

    @GetMapping("upcomingLeaves")
    public List<Leave> getUpcomingLeaves();

    @GetMapping("leaveSold/{id}")
    public SoldeDto leaveSolde(@PathVariable Long id);

    @GetMapping("usedLeaveDays/{id}")
    public LeaveDaysDto usedLeaveDaysByEmpId(@PathVariable Long id);


}
