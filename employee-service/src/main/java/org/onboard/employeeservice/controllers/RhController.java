package org.onboard.employeeservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.onboard.employeeservice.dto.ContractDto;
import org.onboard.employeeservice.dto.EmployeeInputDto;
import org.onboard.employeeservice.dto.EmployeeOutputDto;
import org.onboard.employeeservice.dto.LeaveDto;
import org.onboard.employeeservice.entities.Contract;
import org.onboard.employeeservice.entities.Employee;
import org.onboard.employeeservice.exeptions.EmployeeEmailExistException;
import org.onboard.employeeservice.exeptions.EmployeeIdNotFoundException;
import org.onboard.employeeservice.exeptions.MissingFieldsException;
import org.onboard.employeeservice.models.Leave;
import org.onboard.employeeservice.services.ContractServiceImp;
import org.onboard.employeeservice.services.EmployeeService;
import org.onboard.employeeservice.services.LeaveRestClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("rh")
public class RhController {
    EmployeeService employeeService;
    ContractServiceImp contractService;
    LeaveRestClientService leaveRestClientService;




    @GetMapping("allEmployees")
    public ResponseEntity<Object> getAllEmployees() {
        List<Employee> employees = employeeService.allEmployees();
        return ResponseEntity.status(200).body(employees);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable Long id){
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.status(200).body(employee);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeInputDto employeeInputDto) throws EmployeeEmailExistException, MissingFieldsException {
        if(employeeService.createEmployee(employeeInputDto) != null){
            return ResponseEntity.ok("Account registred successfully.");
        }
        return ResponseEntity.status(402).body("An error has occured");
    }

    @PostMapping("/addContract")
    public ResponseEntity<String> addContract(@RequestBody ContractDto contractDto) throws MissingFieldsException, EmployeeIdNotFoundException {
        Contract contract = contractService.createContract(contractDto);
        if(contract != null){
            if (employeeService.addContractToEmployee(contractDto.getEmployeeId(), contract) != null)
                return ResponseEntity.ok("Contract Created successfully.");
            return ResponseEntity.status(402).body("Employee with id " + contractDto.getEmployeeId() + " not found");
        }
        return ResponseEntity.status(402).body("An error has occured");
    }

    @DeleteMapping("removeEmployee/{id}")
    public ResponseEntity<String> removeEmployee(@PathVariable int id) throws EmployeeIdNotFoundException {
        employeeService.deleteEmployee((long) id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PutMapping("updateEmployee/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody EmployeeInputDto employeeInputDto) throws MissingFieldsException, EmployeeIdNotFoundException {
        EmployeeOutputDto emp = employeeService.updateEmployee(id, employeeInputDto);
        return ResponseEntity.ok("success");
    }

    @GetMapping("LeaveById/{id}")
    public ResponseEntity<Object> leavesById(@PathVariable Long id){
        Leave leave =  leaveRestClientService.getLeaveById(id);
        return ResponseEntity.ok(leave);
    }

    @GetMapping("LeavesByEmpId/{id}")
    public ResponseEntity<Object> leavesByEmpId(@PathVariable Long id){
        List<Leave> leave =  leaveRestClientService.getLeavesByEmpId(id);
        return ResponseEntity.ok(leave);
    }

    @GetMapping("noContractEmployees")
    public ResponseEntity<Object> getEmployeesNoContract() {
        List<Employee> employees = employeeService.getEmployeesWithNoContract();
        return ResponseEntity.status(200).body(employees);
    }

    @GetMapping("allLeaves")
    public ResponseEntity<Object> leaves() {
        List<Leave> leave = leaveRestClientService.getAllLeaves();
        return ResponseEntity.ok(leave);
    }

    @GetMapping("pendingLeaveRequests")
    public ResponseEntity<Object> getPendingLeaveRequests(){
        List<Leave> leaves = leaveRestClientService.getPendingLeave();
        leaves.stream()
                .forEach(leave -> leave.setEmployee(employeeService.getEmployeeById(leave.getEmployeeId())));

        return ResponseEntity.status(200).body(leaves);
    }

    @GetMapping("confirmLeave/{id}")
    public ResponseEntity<String> confirmLeaveById(@PathVariable Long id){
        leaveRestClientService.confirmLeaveById(id);
        return ResponseEntity.status(200).body("Leave request confirmed successfully");
    }

    @GetMapping("rejectLeave/{id}")
    public ResponseEntity<String> rejectLeaveById(@PathVariable Long id){
        leaveRestClientService.rejectLeaveById(id);
        return ResponseEntity.status(200).body("Leave request rejected successfully");
    }

    @DeleteMapping("deleteLeave/{id}")   //to employee
    public ResponseEntity<String> deleteLeaveById(@PathVariable Long id){
        leaveRestClientService.deleteLeaveById(id);
        return ResponseEntity.status(200).body("Leave request deleted successfully");
    }

    @GetMapping("upcomingLeaves")
    public ResponseEntity<Object> upcomingLeaves() {
        List<Leave> leave = leaveRestClientService.getUpcomingLeaves();
        leave.stream()
                .forEach(leave1 -> leave1.setEmployee(employeeService.getEmployeeById(leave1.getEmployeeId())));

        return ResponseEntity.ok(leave);
    }

    @GetMapping("LeaveSoldeByEmpId/{id}")
    public ResponseEntity<Object> leaveSoldeByEmployeeId(@PathVariable Long id){
        return ResponseEntity.ok(leaveRestClientService.leaveSolde(id));
    }

    @GetMapping("usedLeaveDaysByEmpId/{id}")
    public ResponseEntity<Object> usedLeaveDaysByEmpId(@PathVariable Long id){
        return ResponseEntity.ok(leaveRestClientService.usedLeaveDaysByEmpId(id));
    }

}
