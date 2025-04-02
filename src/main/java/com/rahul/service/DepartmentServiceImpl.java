package com.rahul.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.dto.DepartmentDto;
import com.rahul.model.Department;
import com.rahul.repository.IDepartmentRepository;

@Service
public class DepartmentServiceImpl implements IDepartmentService{
	
	@Autowired
	private IDepartmentRepository departmentRepository;

	@Override
	public DepartmentDto createNewOrUpdateDepartment(DepartmentDto departmentDetail) {
		Department department = new Department();
		BeanUtils.copyProperties(departmentDetail, department);
		var response = departmentRepository.saveDepartment(department);
		BeanUtils.copyProperties(response, departmentDetail);
		return departmentDetail;
	}
	
	@Override
	public List<DepartmentDto> findAllDepartment(){
		return departmentRepository.findAllDepartment();
	}
	
	@Override
	public DepartmentDto findDepartmentById(Long deptId) {
		return departmentRepository.findDepartmentById(deptId);
	}
	
	
	@Override
	public DepartmentDto modifyExistingDepartnameById(Long deptId, DepartmentDto modifiedDepartment) {
		DepartmentDto existingDepartment = departmentRepository.findDepartmentById(deptId);
		if (isDepartNameExist(modifiedDepartment.getDepartmentName())) {
			throw new RuntimeException("Department name is already exist.");
		}
		modifiedDepartment.setDeptId(existingDepartment.getDeptId());
		return createNewOrUpdateDepartment(modifiedDepartment);
	}

	private boolean isDepartNameExist(String departmentName) {
		return departmentRepository.isDepartmentNameExist(departmentName);
	}
	
	@Override
	public String deleteDepartment(Long deptId) {
		return departmentRepository.deleteDepartmentById(deptId);
	}

	@Override
	public List<DepartmentDto> findAllDepartmentByIds(List<Long> deptIds) {
		return departmentRepository.findAllDepartmentByIds(deptIds);
	}
	
	

}
