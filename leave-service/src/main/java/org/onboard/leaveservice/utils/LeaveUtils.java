package org.onboard.leaveservice.utils;



import org.onboard.leaveservice.dto.LeaveDto;

public class LeaveUtils {
    public static boolean checkLeaveDtoFields(LeaveDto leaveDto) {
        return leaveDto.getType() == null || leaveDto.getType().isEmpty()
                || leaveDto.getEmployeeId() == null
                || leaveDto.getEndDate() == null
                || leaveDto.getStartDate() == null ;
    }
}
