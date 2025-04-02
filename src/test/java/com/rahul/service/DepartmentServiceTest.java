package com.rahul.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rahul.dto.DepartmentDto;
import com.rahul.model.Department;
import com.rahul.repository.IDepartmentRepository;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
	
	@InjectMocks
	DepartmentServiceImpl serviceImpl;
	
	@Mock
	IDepartmentRepository departmentRepository;
	
	private Department setUpDepartment() {
		Department department = new Department();
		department.setDeptId(10L);
		department.setDepartmentName("Test Department");
		return department;
	}
	
	@Test
	void createNewOrUpdateDepartment() {
		Mockito.when(departmentRepository.saveDepartment(any())).thenReturn(setUpDepartment());
		DepartmentDto newDepartment = new DepartmentDto(10L, "Test Department");
		DepartmentDto actual = serviceImpl.createNewOrUpdateDepartment(newDepartment);
		assertNotNull(actual);
		assertEquals(Long.valueOf("10"), actual.getDeptId());
		assertEquals("Test Department", actual.getDepartmentName());
	}
	
	@Test
	void findAllDepartment() {
		serviceImpl.findAllDepartment();
		Mockito.verify(departmentRepository, times(1)).findAllDepartment();
	}
	
	@Test
	void findDepartmentById() {
		serviceImpl.findDepartmentById(anyLong());
		Mockito.verify(departmentRepository, times(1)).findDepartmentById(anyLong());
	}
	
	@Test
	void deleteDepartmentById() {
		serviceImpl.deleteDepartment(anyLong());
		Mockito.verify(departmentRepository, times(1)).deleteDepartmentById(anyLong());
	}
	
	@Test
	void modifyExistingDepartnameById() {
		Long deptId = 11L;
		Department department = setUpDepartment();
		department.setDeptId(deptId);
		department.setDepartmentName("Test Department2");
		DepartmentDto modifyDepartment = new DepartmentDto(deptId, "Test Department2");
		Mockito.when(departmentRepository.findDepartmentById(deptId)).thenReturn(modifyDepartment);
		Mockito.when(departmentRepository.isDepartmentNameExist(anyString())).thenReturn(Boolean.FALSE);
		Mockito.lenient().when(departmentRepository.saveDepartment(any())).thenReturn(department);
		DepartmentDto actual = serviceImpl.modifyExistingDepartnameById(Long.valueOf("11"), modifyDepartment);
		assertEquals(Long.valueOf("11"), actual.getDeptId());
		assertEquals("Test Department2", actual.getDepartmentName());
		
		Mockito.when(departmentRepository.isDepartmentNameExist(anyString())).thenReturn(Boolean.TRUE);
		assertThrows(RuntimeException.class, ()-> serviceImpl.modifyExistingDepartnameById(deptId, modifyDepartment));
		assertThatThrownBy(()-> serviceImpl.modifyExistingDepartnameById(deptId, modifyDepartment))
		.isInstanceOf(RuntimeException.class)
		.hasMessage("Department name is already exist.");
		
	}
	
	

}
