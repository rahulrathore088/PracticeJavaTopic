package com.rahul.service;

import java.util.List;

import com.rahul.model.EmployeWithDepartment;
import com.rahul.model.EmployeeDto;
import com.rahul.model.EmployeeResponse;

import jakarta.validation.Valid;

public interface EmployeeService {

	EmployeeResponse addEmployee(@Valid EmployeeDto employeedto);

	List<EmployeeResponse> findAllEmployee();

	EmployeeResponse findByEmpId(Long empId);

	String deleteByEmpId(Long empId);

	List<EmployeWithDepartment> getEmployeeDetailWithDepartment();

}
