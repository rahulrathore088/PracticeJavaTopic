package com.rahul.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rahul.model.EmployeWithDepartment;
import com.rahul.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import jakarta.transaction.Transactional;

@Repository
public class EmployeeRepoImpl implements EmployeeRepo {

	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public Employee saveEmployee(Employee detail) {
		manager.persist(detail);
		return detail;

	}

	@Override
	public List<Employee> findAll() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		query.select(root);
		return manager.createQuery(query).getResultList();
	}

	@Override
	public Employee findByEmpId(Long empId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		query.where(builder.equal(root.get("empId"), empId));
		return manager.createQuery(query).getSingleResult();
	}

	@Override
	@Transactional
	public void deleteByEmpId(Long empId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaDelete<Employee> query = builder.createCriteriaDelete(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		query.where(builder.equal(root.get("empId"), empId));
		System.out.println(manager.createQuery(query).executeUpdate());
	}

	@Override
	public List<EmployeWithDepartment> findAllEmployee() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EmployeWithDepartment> query = builder.createQuery(EmployeWithDepartment.class);
		Root<Employee> root = query.from(Employee.class);
		query.select(builder.construct(EmployeWithDepartment.class, root.get("empId"), root.get("dob"),
				root.get("firstName"), root.get("lastName"), root.get("hireDate"), root.get("gender"),
				root.get("deptId")));
		return manager.createQuery(query).getResultList();
	}
}
