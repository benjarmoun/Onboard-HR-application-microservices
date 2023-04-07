package org.onboard.leaveservice.utils;



import org.onboard.leaveservice.dto.LeaveDto;

public class LeaveUtils {
    public static boolean checkLeaveDtoFields(LeaveDto leaveDto) {
        return leaveDto.getType() == null || leaveDto.getType().isEmpty()
                || leaveDto.getStatus() == null || leaveDto.getStatus().isEmpty()
                || leaveDto.getNumbOfDays() == null
                || leaveDto.getEmployeeId() == null
                || leaveDto.getEndDate() == null
                || leaveDto.getStartDate() == null ;
    }
}
