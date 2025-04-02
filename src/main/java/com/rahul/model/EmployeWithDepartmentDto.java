package com.rahul.model;

import java.io.Serializable;

public class EmployeWithDepartmentDto implements Serializable {

	private static final long serialVersionUID = -3715377823637600076L;

	private Long deptId;
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
