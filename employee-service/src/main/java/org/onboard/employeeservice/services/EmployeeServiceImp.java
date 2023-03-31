package org.onboard.employeeservice.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.onboard.employeeservice.dto.EmployeeInputDto;
import org.onboard.employeeservice.dto.EmployeeOutputDto;
import org.onboard.employeeservice.entities.Contract;
import org.onboard.employeeservice.entities.Employee;
import org.onboard.employeeservice.exeptions.EmployeeEmailExistException;
import org.onboard.employeeservice.exeptions.EmployeeIdNotFoundException;
import org.onboard.employeeservice.exeptions.MissingFieldsException;
import org.onboard.employeeservice.helpers.Enum;
import org.onboard.employeeservice.helpers.JwtUtils;
import org.onboard.employeeservice.mappers.MapperService;
import org.onboard.employeeservice.repositories.ContractRepository;
import org.onboard.employeeservice.repositories.EmployeeRepository;
import org.onboard.employeeservice.utils.EmployeeUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeServiceImp implements EmployeeService{
    private MapperService mapperService;
    private EmployeeRepository employeeRepository;
    private ContractRepository contractRepository;
    private final JwtUtils jwtUtils;





    @Override
    public List<Employee> allEmployees() {
        return employeeRepository.findAll().stream()
//                .map(mapperService::fromEmployee)
                .toList();
    }

    @Override
    public Employee createEmployee(EmployeeInputDto employeeInputDto) throws MissingFieldsException, EmployeeEmailExistException {
        if(EmployeeUtils.checkEmployeeInputDtoFields(employeeInputDto))
            throw new MissingFieldsException();
        employeeInputDto.setRole(Enum.role.Employee.toString());
        System.out.println("role set: "+ employeeInputDto);
        Employee employee = this.mapperService.inputDtoToEmployee(employeeInputDto);
        System.out.println("employee set: "+ employee);
        Employee emp = employeeRepository.save(employee);
        System.out.println("emp: "  +emp);
        return emp;
    }

    @Override
    public void deleteEmployee(Long id) throws EmployeeIdNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new EmployeeIdNotFoundException(id));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeOutputDto updateEmployee(Long id, EmployeeInputDto employeeInputDto) throws MissingFieldsException, EmployeeIdNotFoundException {
        if (EmployeeUtils.checkEmployeeInputDtoFields(employeeInputDto))
            throw new MissingFieldsException();
        employeeRepository.findById(id).orElseThrow(()-> new EmployeeIdNotFoundException(id));
        Employee employee = mapperService.inputDtoToEmployee(employeeInputDto);
        employee.setId(id);
        System.out.println("employee save" +employee);
        return mapperService.fromEmployee(employeeRepository.save(employee));
    }

    @Override
    public UserDetails findByEmail(String email) {
        Employee user = employeeRepository.findAll()
                .stream()
                .filter(u -> (u.getEmail()).equals(email))
                .findFirst()
                .orElse(null);

        System.out.println(user);
        if(user != null && user.getRole().equals(Enum.role.Employee.toString())){
            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(Enum.role.Employee.toString())));
        } else if (user.getRole().equals(Enum.role.RH.toString())) {
            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(Enum.role.RH.toString())));
        }
        return null;
    }

    @Override
    public Employee getEmployeeByEmail(String email){
        Optional employee = employeeRepository.findByEmail(email);
        return employee.isPresent() ? (Employee) employee.get() : null;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional employee = employeeRepository.findById(id);
        return employee.isPresent() ? (Employee) employee.get() : null;
    }

    @Override
    public EmployeeOutputDto addContractToEmployee(Long employeeId, Contract contract) throws EmployeeIdNotFoundException {
        Employee emp = employeeRepository.findById(employeeId).orElse(null);
        if(emp == null){
            contractRepository.delete(contract);
//            throw new EmployeeIdNotFoundException(employeeId);
            return null;
        }
        emp.setContract(contract);

        return mapperService.fromEmployee(employeeRepository.save(emp));
    }

    @Override
    public String getEmail(HttpServletRequest request){

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String userEmail;
        final String jwtToken;

        jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwtToken);
        System.out.println(authHeader);
        System.out.println(userEmail);
        System.out.println(jwtToken);
        if(userEmail != null){
            return userEmail;
        }
        return null;
    }
}
