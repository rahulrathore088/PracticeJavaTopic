package com.rahul.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.model.EmployeWithDepartment;
import com.rahul.model.EmployeeDto;
import com.rahul.model.EmployeeResponse;
import com.rahul.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}
	
	@PostMapping("/createEmployee")
	public EmployeeResponse create(@RequestBody @Valid EmployeeDto employeedto) {
		return service.addEmployee(employeedto);
	}
	
	@GetMapping("/allEmployee")
	public List<EmployeeResponse> findAllEmployee(){
		return service.findAllEmployee();
	}
	
	@GetMapping("/{empId}")
	public EmployeeResponse findByEmpId(@PathVariable Long empId) {
		return service.findByEmpId(empId);
	}
	
	@DeleteMapping("/{empId}")
	public String deleteByEmpId(@PathVariable Long empId) {
		return service.deleteByEmpId(empId);
	}
	
	@GetMapping("/withDepartment")
	public List<EmployeWithDepartment> getEmployeeDetailWithDepartment(){
		return service.getEmployeeDetailWithDepartment();
		
	}
	
	
	
	
}
