package com.rahul.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.rahul.AbstractTestContainer;
import com.rahul.dto.DepartmentDto;
import com.rahul.model.Department;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DepartmentRepositoryTest extends AbstractTestContainer{
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	private Long deptId = Long.MAX_VALUE;
	
	@BeforeEach
	void setUp() {
		Department department = new Department();
		department.setDepartmentName("Test Department");
		departmentRepository.saveDepartment(department);
		deptId = department.getDeptId();
	}
	
	@AfterEach
	void tearDown() {
		departmentRepository.deleteAllDepartment();
	}
	
	@Test
	void shoudFindByDepartmentName() {
		boolean isExist = departmentRepository.isDepartmentNameExist("Test Department");
		assertTrue(isExist);
	}
	
	@Test
	void shoudFindAllDepartment() {
		List<DepartmentDto> departmentList = departmentRepository.findAllDepartment();
		assertEquals(1, departmentList.size());
		assertEquals("Test Department", departmentList.get(0).getDepartmentName());
	}
	
	@Test
	void shoudDeleteByDepartmentId() {
		DepartmentDto departmentDto = departmentRepository.findDepartmentById(deptId);
		String msg = departmentRepository.deleteDepartmentById(departmentDto.getDeptId());
		assertEquals("Success", msg);
		String msg2 = departmentRepository.deleteDepartmentById(departmentDto.getDeptId());
		assertEquals("Department is not found to delete", msg2);
	}
	
	@Test
	void shoudNotFindByDepartmentName() {
		boolean isExist = departmentRepository.isDepartmentNameExist("Test Department2");
		assertThat(isExist).isFalse();
	}
	
	@Test
	void shouldFailedToFetchDepartment() {
		assertThrows(RuntimeException.class, ()-> departmentRepository.findDepartmentById(Long.valueOf("10")));
	}

}
