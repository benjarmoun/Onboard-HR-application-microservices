package org.onboard.leaveservice.services;

import org.onboard.leaveservice.dto.LeaveDto;
import org.onboard.leaveservice.dto.SoldeDto;
import org.onboard.leaveservice.entites.Leave;
import org.onboard.leaveservice.exceptions.LeaveIdNotFoundException;
import org.onboard.leaveservice.exceptions.MissingFieldsException;

import java.util.List;

public interface LeaveService {
    List<Leave> allLeaves();

    List<Leave> getPendingLeaves();

    Leave createLeaveRequest(LeaveDto leaveDto) throws MissingFieldsException;
    void deleteLeave(Long id) throws LeaveIdNotFoundException;
    LeaveDto updateLeave(Long id, LeaveDto leaveDto) throws MissingFieldsException, LeaveIdNotFoundException;


    List<Leave> getLeaveByEmpId(Long id);

    Leave getLeaveById(Long id);

    Leave confirmLeave(Long id) throws LeaveIdNotFoundException;
    Leave rejectLeave(Long id) throws LeaveIdNotFoundException;

    SoldeDto leaveSoldeByEmpId(Long id);


    Long usedLeavedaysByEmpId(Long id);

    List<Leave> comingLeaves();
}
