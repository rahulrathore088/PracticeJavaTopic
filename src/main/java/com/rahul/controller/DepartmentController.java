package com.rahul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.dto.DepartmentDto;
import com.rahul.dto.RequestDepartment;
import com.rahul.service.IDepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
	
	@Autowired
	private IDepartmentService departmentService;
	
	@PostMapping("/createDepartment")
	public DepartmentDto createNewDepartment(@RequestBody DepartmentDto departmentDetail) {
		return departmentService.createNewOrUpdateDepartment(departmentDetail);
	}
	
	@GetMapping("/findAllDepartment")
	public List<DepartmentDto> findAllDepartment(){
		return departmentService.findAllDepartment();
	}
	
	@GetMapping({"/{deptId}"})
	public DepartmentDto findDepartmentById(@PathVariable Long deptId) {
		return departmentService.findDepartmentById(deptId);
	}
	
	@PutMapping("/{deptId}")
	public DepartmentDto modifiedDepartName(@PathVariable Long deptId, @RequestBody DepartmentDto modifiedDetail) {
		return departmentService.modifyExistingDepartnameById(deptId, modifiedDetail);
	}
	
	@DeleteMapping("/{deptId}")
	public String deleteDepartmentById(@PathVariable Long deptId) {
		return departmentService.deleteDepartment(deptId);
	}
	
	@PostMapping({"/findAllDepartmentByIds"})
	public List<DepartmentDto> findAllDepartmentByIds(@RequestBody List<Long> departmentIds) {
		return departmentService.findAllDepartmentByIds(departmentIds);
	}

}
