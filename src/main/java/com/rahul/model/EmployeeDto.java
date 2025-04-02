package com.rahul.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EmployeeDto implements Serializable {
	
	private static final long serialVersionUID = 4675940708193033884L;

	@Past(message = "Date of birth is required.")
	private LocalDate dob;
	
	private String firstName;
	
	private String lastName;
	
	@PastOrPresent(message = "Hire date past or present is required.")
	private LocalDate hireDate;
	
	private Gender gender;

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
