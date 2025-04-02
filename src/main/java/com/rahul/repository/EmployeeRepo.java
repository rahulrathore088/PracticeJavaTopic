package com.rahul.repository;

import java.util.List;

import com.rahul.model.EmployeWithDepartment;
import com.rahul.model.Employee;

public interface EmployeeRepo {

	Employee saveEmployee(Employee detail);

	List<Employee> findAll();

	Employee findByEmpId(Long empId);

	void deleteByEmpId(Long empId);

	List<EmployeWithDepartment> findAllEmployee();

}
