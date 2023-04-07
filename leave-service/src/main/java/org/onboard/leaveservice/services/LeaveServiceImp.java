package org.onboard.leaveservice.services;

import lombok.AllArgsConstructor;
import org.onboard.leaveservice.dto.LeaveDto;
import org.onboard.leaveservice.dto.SoldeDto;
import org.onboard.leaveservice.entites.Leave;
import org.onboard.leaveservice.exceptions.LeaveIdNotFoundException;
import org.onboard.leaveservice.exceptions.MissingFieldsException;
import org.onboard.leaveservice.helpers.Enum;
import org.onboard.leaveservice.mappers.MapperService;
import org.onboard.leaveservice.repositories.LeaveRepository;
import org.onboard.leaveservice.utils.LeaveUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LeaveServiceImp implements LeaveService{
    private MapperService mapperService;
    final LeaveRepository leaveRepository;

    @Override
    public List<Leave> allLeaves() {
        return leaveRepository.findAll();
    }

    @Override
    public Leave getLeaveById(Long id) {
        Optional leave = leaveRepository.findById(id);
        return leave.isPresent() ? (Leave) leave.get() : null;
    }

    @Override
    public List<Leave> getPendingLeaves() {
        List<Leave> leave = leaveRepository.findAll();
        List<Leave> pendingLeaves = leave.stream()
                .filter(e -> e.getStatus().equals(Enum.status.Pending.toString()))
                .collect(Collectors.toList());
        return pendingLeaves;
    }

    @Override
    public Leave createLeaveRequest(LeaveDto leaveDto) throws MissingFieldsException {
        if(LeaveUtils.checkLeaveDtoFields(leaveDto))
            throw new MissingFieldsException();
        Date startDate = leaveDto.getStartDate();
        Date endDate = leaveDto.getEndDate();
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        Long numOfDays = diff + 1; // add 1 to include the first and last day
        leaveDto.setNumbOfDays(numOfDays);
        Leave leave = this.mapperService.dtoToLeave(leaveDto);
        return leaveRepository.save(leave);
    }

    @Override
    public void deleteLeave(Long id) throws LeaveIdNotFoundException {
        Leave leave = leaveRepository.findById(id).orElseThrow(()-> new LeaveIdNotFoundException(id));
        leaveRepository.delete(leave);
    }

    @Override
    public LeaveDto updateLeave(Long id, LeaveDto leaveDto) throws MissingFieldsException, LeaveIdNotFoundException {
        if (LeaveUtils.checkLeaveDtoFields(leaveDto))
            throw new MissingFieldsException();
        leaveRepository.findById(id).orElseThrow(()-> new LeaveIdNotFoundException(id));
        Leave leave = mapperService.dtoToLeave(leaveDto);
        leave.setId(id);
        return mapperService.fromLeave(leaveRepository.save(leave));
    }

    @Override
    public List<Leave> getLeaveByEmpId(Long id) {
        List<Leave> leave = leaveRepository.findByEmployeeId(id);
        return leave;
    }

    @Override
    public Leave confirmLeave(Long id) throws LeaveIdNotFoundException {
        Optional<Leave> leave = leaveRepository.findById(id);
        if(leave.isPresent()){
            Leave leave1 = leave.get();
            leave1.setStatus(Enum.status.Confirmed.toString());
            return leaveRepository.save(leave1);
        }else
            throw new LeaveIdNotFoundException(id);
    }

    @Override
    public Leave rejectLeave(Long id) throws LeaveIdNotFoundException {
        Optional<Leave> leave = leaveRepository.findById(id);
        if(leave.isPresent()){
            Leave leave1 = leave.get();
            leave1.setStatus(Enum.status.Rejected.toString());
            return leaveRepository.save(leave1);
        }else
            throw new LeaveIdNotFoundException(id);
    }

    @Override
    public Long usedLeavedaysByEmpId(Long id) {
        List<Leave> leave = getLeaveByEmpId(id);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startOfYear = calendar.getTime();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        Date endOfYear = calendar.getTime();
        Long solde = leave.stream()
                .filter(e -> e.getStatus().equals(Enum.status.Confirmed.toString()))
                .filter(e -> e.getStartDate().compareTo(startOfYear) >= 0 && e.getStartDate().compareTo(endOfYear) <= 0)
                .map(e -> {
                    Date startDate = e.getStartDate().compareTo(startOfYear) < 0 ? startOfYear : e.getStartDate();
                    Date endDate = e.getEndDate().compareTo(endOfYear) > 0 ? endOfYear : e.getEndDate();
                    long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    return  diff;
                })
                .reduce(0L, Long::sum);
        SoldeDto soldeDto = new SoldeDto();
        soldeDto.setSolde(solde);
        return solde;
    }

    @Override
    public SoldeDto leaveSoldeByEmpId(Long id){
        Long solde = 18 -  usedLeavedaysByEmpId(id);
        SoldeDto soldeDto = new SoldeDto();
        soldeDto.setSolde(solde);
        return soldeDto;
    }

    @Override
    public List<Leave> comingLeaves() {
        List<Leave> leave =  leaveRepository.findAll();
        List<Leave> filteredLeaves = leave.stream()
                .filter(leave1 -> leave1.getEndDate().compareTo(new Date()) > 0)
                .sorted(Comparator.comparing(Leave::getEndDate))
                .collect(Collectors.toList());
        return filteredLeaves;
    }

}
