package com.rahul.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rahul.model.EmployeWithDepartment;
import com.rahul.model.EmployeWithDepartmentDto;
import com.rahul.model.Employee;
import com.rahul.model.EmployeeDto;
import com.rahul.model.EmployeeResponse;
import com.rahul.repository.EmployeeRepo;

import jakarta.validation.Valid;

@Service
public class EmployeeServiceImp implements EmployeeService {

	@Autowired
	private EmployeeRepo empRepo;

	@Autowired
	private RestTemplate template;

	@Override
	public EmployeeResponse addEmployee(@Valid EmployeeDto employeedto) {
		Employee empreq = new Employee();
		EmployeeResponse response = new EmployeeResponse();
		BeanUtils.copyProperties(employeedto, empreq);
		var savedEmp = empRepo.saveEmployee(empreq);
		BeanUtils.copyProperties(savedEmp, response);
		return response;
	}

	@Override
	public List<EmployeeResponse> findAllEmployee() {
		List<Employee> empList = empRepo.findAll();
		List<EmployeeResponse> responses = new ArrayList<>();
		for (Employee emp : empList) {
			EmployeeResponse response = new EmployeeResponse();
			BeanUtils.copyProperties(emp, response);
			responses.add(response);
		}
		return responses;
	}

	@Override
	public EmployeeResponse findByEmpId(Long empId) {
		Employee emp = empRepo.findByEmpId(empId);
		EmployeeResponse response = new EmployeeResponse();
		BeanUtils.copyProperties(emp, response);
		return response;
	}

	@Override
	public String deleteByEmpId(Long empId) {
		empRepo.deleteByEmpId(empId);
		return "Success";
	}

	@Override
	public List<EmployeWithDepartment> getEmployeeDetailWithDepartment() {
		List<EmployeWithDepartment> empList = empRepo.findAllEmployee();
		List<Long> deptIds = empList.stream().map(x -> x.getDeptId()).toList();
		List<EmployeWithDepartmentDto> list = getDepartmentWithEmplyee(deptIds);
		Map<Long, EmployeWithDepartmentDto> departmentMap = list.stream()
				.collect(Collectors.toMap(x -> x.getDeptId(), Function.identity()));
		empList.stream().forEach(emp -> {
			EmployeWithDepartmentDto deptDetail = departmentMap.getOrDefault(emp.getDeptId(), null);
			emp.setDepartmentDetail(deptDetail);
		});
		return empList;
	}

	private List<EmployeWithDepartmentDto> getDepartmentWithEmplyee(List<Long> deptIds) {
		HttpEntity<?> request = new HttpEntity<>(deptIds);
		ResponseEntity<List<EmployeWithDepartmentDto>> departmentResponse = template.exchange(
				"http://localhost:8081/api/department/findAllDepartmentByIds", HttpMethod.POST, request,
				new ParameterizedTypeReference<List<EmployeWithDepartmentDto>>() {
				});
		return departmentResponse.getBody();
	}
}
