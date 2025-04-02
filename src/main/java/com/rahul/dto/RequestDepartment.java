package com.rahul.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestDepartment implements Serializable{

	private static final long serialVersionUID = 655454985888258054L;
	
	List<Long> departmentIds;

	public List<Long> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<Long> departmentIds) {
		this.departmentIds = departmentIds;
	}

}
