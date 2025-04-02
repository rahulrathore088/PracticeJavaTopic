package com.rahul.service;

import java.util.List;

import com.rahul.dto.DepartmentDto;

public interface IDepartmentService {
	
	DepartmentDto createNewOrUpdateDepartment(DepartmentDto departmentDetail);

	List<DepartmentDto> findAllDepartment();

	DepartmentDto findDepartmentById(Long deptId);

	DepartmentDto modifyExistingDepartnameById(Long deptId, DepartmentDto modifiedDepartment);

	String deleteDepartment(Long deptId);

	List<DepartmentDto> findAllDepartmentByIds(List<Long> deptIds);
	

}
