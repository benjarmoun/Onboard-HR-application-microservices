package org.onboard.employeeservice.controllers;

import org.onboard.employeeservice.dto.AuthenticationRequest;
import org.onboard.employeeservice.entities.Employee;
import org.onboard.employeeservice.helpers.EmployeeLoggedIn;
import org.onboard.employeeservice.helpers.Enum;
import org.onboard.employeeservice.helpers.JwtUtils;
import org.onboard.employeeservice.repositories.EmployeeRepository;
import org.onboard.employeeservice.services.EmployeeServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeServiceImp employeeService;
    private final JwtUtils jwtUtils;
    private final EmployeeRepository employeeRepository;

    @PostMapping("rh")
    public ResponseEntity<Object> agentAuthentication(
            @RequestBody AuthenticationRequest request
    ){
        if (setAuthenticationManager(request.getEmail(), request.getPassword())){
            if(employeeService.getEmployeeByEmail(request.getEmail()).getRole().equals(Enum.role.RH.toString())){
                final UserDetails user = employeeService.findByEmail(request.getEmail());

                if(user != null){
                    EmployeeLoggedIn loggedIn = new EmployeeLoggedIn();
                    Employee employee = employeeService.getEmployeeByEmail(user.getUsername());
                    employee.setPassword(null);
                    loggedIn.setEmployee(employee);
                    loggedIn.setToken(jwtUtils.generateToken(user));
                    return ResponseEntity.ok(loggedIn);
                }
                return ResponseEntity.status(400).body("An error has occurred");
            }
            return ResponseEntity.status(400).body("Not authorized");
        }
        return ResponseEntity.status(400).body("Wrong Email or Password");
    }


    @PostMapping("employee")
    public ResponseEntity<Object> customerAuthentication(
            @RequestBody AuthenticationRequest request
    ){
        if (setAuthenticationManager(request.getEmail(), request.getPassword())) {
//            if (employeeService.getEmployeeByEmail(request.getEmail()).getRole().equals(Enum.role.Employee.toString())) {
                final UserDetails userCustomer = employeeService.findByEmail(request.getEmail());
                if (userCustomer != null) {
                    EmployeeLoggedIn loggedIn = new EmployeeLoggedIn();
                    Employee employee = employeeService.getEmployeeByEmail(request.getEmail());
                    employee.setPassword(null);
                    loggedIn.setEmployee(employee);
                    loggedIn.setToken(jwtUtils.generateToken(userCustomer));
                    return ResponseEntity.ok(loggedIn);
                }
                return ResponseEntity.status(400).body("Some error has occurred");
//            }
//            return ResponseEntity.status(400).body("Not authorized");
        }
        return ResponseEntity.status(400).body("Wrong Email or Password");
    }

    public boolean setAuthenticationManager(String email, String password){
        UsernamePasswordAuthenticationToken tkn = new UsernamePasswordAuthenticationToken(email, password);
        try {
            if (authenticationManager.authenticate(tkn).isAuthenticated()){
                return true;
            }
        } catch (AuthenticationException e) {
            return false;
        }
        return false;
    }

}
