package com.rahul.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "Department_Detail", indexes = { @Index(columnList = "DEPARTMENT_NAME", name = "DEPT_NAME_IDX") }, uniqueConstraints = {
		@UniqueConstraint(columnNames = "DEPARTMENT_NAME", name = "DEPT_NAME_UNIQ") })
public class Department {
	
	@Id
	@Column(name = "DEPT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deptId;
	
	@Column(name = "DEPARTMENT_NAME")
	@NotBlank
	private String departmentName;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
