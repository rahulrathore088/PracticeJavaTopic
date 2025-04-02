package com.rahul.repository;

import java.util.List;

import com.rahul.dto.DepartmentDto;
import com.rahul.model.Department;

public interface IDepartmentRepository {

	Department saveDepartment(Department department);

	List<DepartmentDto> findAllDepartment();

	DepartmentDto findDepartmentById(Long deptId);

	boolean isDepartmentNameExist(String departmentName);

	String deleteDepartmentById(Long deptId);

	void deleteAllDepartment();

	List<DepartmentDto> findAllDepartmentByIds(List<Long> deptIds);

}
