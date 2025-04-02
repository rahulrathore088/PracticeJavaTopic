package com.rahul.model;

import java.io.Serializable;
import java.time.LocalDate;

public class EmployeWithDepartment implements Serializable {

	private static final long serialVersionUID = 8105120162362859225L;

	private Long empId;

	private LocalDate dob;

	private String firstName;

	private String lastName;

	private LocalDate hireDate;

	private Gender gender;
	
	private Long deptId;

	private EmployeWithDepartmentDto departmentDetail;

	public EmployeWithDepartment() {
	}

	public EmployeWithDepartment(Long empId, LocalDate dob, String firstName, String lastName, LocalDate hireDate,
			Gender gender, Long deptId) {
		this.empId = empId;
		this.dob = dob;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hireDate = hireDate;
		this.gender = gender;
		this.deptId = deptId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public EmployeWithDepartmentDto getDepartmentDetail() {
		return departmentDetail;
	}

	public void setDepartmentDetail(EmployeWithDepartmentDto departmentDetail) {
		this.departmentDetail = departmentDetail;
	}

}
