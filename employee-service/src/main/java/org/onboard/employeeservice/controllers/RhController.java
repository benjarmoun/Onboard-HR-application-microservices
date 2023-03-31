package org.onboard.employeeservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.onboard.employeeservice.dto.ContractDto;
import org.onboard.employeeservice.dto.EmployeeInputDto;
import org.onboard.employeeservice.dto.EmployeeOutputDto;
import org.onboard.employeeservice.entities.Contract;
import org.onboard.employeeservice.entities.Employee;
import org.onboard.employeeservice.exeptions.EmployeeEmailExistException;
import org.onboard.employeeservice.exeptions.EmployeeIdNotFoundException;
import org.onboard.employeeservice.exeptions.MissingFieldsException;
import org.onboard.employeeservice.services.ContractServiceImp;
import org.onboard.employeeservice.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("rh")
public class RhController {
    EmployeeService employeeService;
    ContractServiceImp contractService;




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
    @GetMapping("employee")
    public ResponseEntity<Object> getAccountByEmail(HttpServletRequest httpSerReq){
        String email = employeeService.getEmail(httpSerReq);
        return ResponseEntity.ok(employeeService.getEmployeeByEmail(email));
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
        System.out.println("input: "+ contractDto);
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
        System.out.println("             Dkheeeeerrrrrl           " + employeeInputDto);
        EmployeeOutputDto emp = employeeService.updateEmployee(id, employeeInputDto);
        System.out.println("emmmmmmmp "+ emp);
        return ResponseEntity.ok("success");
    }
}
