package com.rahul.dto;

import java.io.Serializable;

public class DepartmentDto implements Serializable{
	
	private static final long serialVersionUID = 4084178766430093337L;

	private Long deptId;
	
	private String departmentName;

	public DepartmentDto(Long deptId, String departmentName) {
		super();
		this.deptId = deptId;
		this.departmentName = departmentName;
	}

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
