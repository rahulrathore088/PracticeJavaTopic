package com.rahul.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.rahul.AbstractTestContainer;
import com.rahul.dto.DepartmentDto;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DepartmentControllerTest extends AbstractTestContainer{

	private static final String API_BASE = "/api/department";

	@Autowired
	private TestRestTemplate template;

	@Test
	void createDepartment() {
		DepartmentDto request = new DepartmentDto(null, "Test Department");
		ResponseEntity<DepartmentDto> response = template.exchange(API_BASE + "/createDepartment", HttpMethod.POST,
				new HttpEntity<>(request), new ParameterizedTypeReference<DepartmentDto>() {
				});
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		ResponseEntity<List<DepartmentDto>> responseList = template.exchange(API_BASE + "/findAllDepartment",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<DepartmentDto>>() {
				});
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DepartmentDto createdDepartment = Objects.requireNonNull(responseList.getBody()).stream()
				.filter(x -> x.getDepartmentName().equals(request.getDepartmentName())).findFirst().orElseThrow();
		assertThat(createdDepartment.getDeptId()).isNotNull();
		assertThat(createdDepartment.getDepartmentName().equals(request.getDepartmentName()));
	}

	@Test
	void deleteDepartment() {
		DepartmentDto request = new DepartmentDto(null, "Test Department2");
		ResponseEntity<DepartmentDto> response = template.exchange(API_BASE + "/createDepartment", HttpMethod.POST,
				new HttpEntity<>(request), new ParameterizedTypeReference<DepartmentDto>() {
				});
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DepartmentDto department = Objects.requireNonNull(response.getBody());
		ResponseEntity<String> deleteResponse = template.exchange(API_BASE + "/" + department.getDeptId(),
				HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
				});
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertEquals("Success", deleteResponse.getBody());
	}

	@Test
	void findDepartment() {
		DepartmentDto request = new DepartmentDto(null, "Test Department3");
		ResponseEntity<DepartmentDto> response = template.exchange(API_BASE + "/createDepartment", HttpMethod.POST,
				new HttpEntity<>(request), new ParameterizedTypeReference<DepartmentDto>() {
				});
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DepartmentDto department = Objects.requireNonNull(response.getBody());
		ResponseEntity<DepartmentDto> fetchResponse = template.exchange(API_BASE + "/" + department.getDeptId(),
				HttpMethod.GET, null, new ParameterizedTypeReference<DepartmentDto>() {
				});
		assertThat(fetchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(fetchResponse.getBody().getDepartmentName()).isEqualTo("Test Department3");

	}

	@Test
	void updateDepartment() {
		DepartmentDto request = new DepartmentDto(null, "Test Department4");
		ResponseEntity<DepartmentDto> response = template.exchange(API_BASE + "/createDepartment", HttpMethod.POST,
				new HttpEntity<>(request), new ParameterizedTypeReference<DepartmentDto>() {
				});
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DepartmentDto department = Objects.requireNonNull(response.getBody());
		request.setDepartmentName("Test Department5");
		ResponseEntity<DepartmentDto> fetchResponse = template.exchange(API_BASE + "/" + department.getDeptId(),
				HttpMethod.PUT, new HttpEntity<>(request), new ParameterizedTypeReference<DepartmentDto>() {
				});
		assertThat(fetchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		DepartmentDto updatedDepartment = Objects.requireNonNull(fetchResponse.getBody());
		assertThat(updatedDepartment.getDepartmentName()).isEqualTo("Test Department5");
		assertThat(updatedDepartment.getDeptId()).isEqualTo(department.getDeptId());
	}

}
