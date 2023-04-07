package org.onboard.leaveservice.mappers;


import org.onboard.leaveservice.dto.LeaveDto;
import org.onboard.leaveservice.entites.Leave;
import org.onboard.leaveservice.helpers.Enum;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class MapperService {
    public LeaveDto fromLeave(Leave leave){
        LeaveDto leaveDto = new LeaveDto();
        BeanUtils.copyProperties(leave, leaveDto);
        return leaveDto;
    }
    public Leave dtoToLeave(LeaveDto employeeInputDto){
        Leave leave = Leave.builder()
                .employeeId(employeeInputDto.getEmployeeId())
                .numbOfDays(employeeInputDto.getNumbOfDays())
                .startDate(employeeInputDto.getStartDate())
                .endDate(employeeInputDto.getEndDate())
                .status(Enum.status.Pending.toString())
                .type(employeeInputDto.getType())
                .build();
//        BeanUtils.copyProperties(employee, employeeInputDto);
        return leave;
    }

}
