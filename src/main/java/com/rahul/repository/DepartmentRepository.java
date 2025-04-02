package com.rahul.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.rahul.dto.DepartmentDto;
import com.rahul.model.Department;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class DepartmentRepository implements IDepartmentRepository{
	
	@PersistenceContext
	EntityManager manager;

	@Override
	@Transactional
	public Department saveDepartment(Department department) {
		if (Objects.nonNull(department.getDeptId()) && !manager.contains(department)) {
			manager.merge(department);
		} else {
			manager.persist(department);
		}
		return department;
	}

	@Override
	public List<DepartmentDto> findAllDepartment() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<DepartmentDto> query = builder.createQuery(DepartmentDto.class);
		Root<Department> root = query.from(Department.class);
		query.select(builder.construct(DepartmentDto.class, root.get("deptId"), root.get("departmentName")));
		return manager.createQuery(query).getResultList();
	}

	@Override
	public DepartmentDto findDepartmentById(Long deptId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<DepartmentDto> query = builder.createQuery(DepartmentDto.class);
		Root<Department> root = query.from(Department.class);
		query.where(builder.equal(root.get("deptId"), deptId));
		query.select(builder.construct(DepartmentDto.class, root.get("deptId"), root.get("departmentName")));
		try {
			return manager.createQuery(query).getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			throw new RuntimeException("Department doest not exist for department id : " + deptId);
		}
	}

	@Override
	public boolean isDepartmentNameExist(String departmentName) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Department> root = query.from(Department.class);
		query.where(builder.equal(root.get("departmentName"), departmentName));
		query.select(builder.count(root.get("deptId")));
		return manager.createQuery(query).getSingleResult() > 0;
	}

	@Override
	@Transactional
	public String deleteDepartmentById(Long deptId) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaDelete<Department> query = builder.createCriteriaDelete(Department.class);
		Root<Department> root = query.from(Department.class);
		query.where(builder.equal(root.get("deptId"), deptId));
		int rowCount = manager.createQuery(query).executeUpdate();
		if (rowCount > 0) {
			return "Success";
		}
		return "Department is not found to delete";
	}

	@Override
	@Transactional
	public void deleteAllDepartment() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaDelete<Department> query = builder.createCriteriaDelete(Department.class);
		manager.createQuery(query).executeUpdate();
	}

	@Override
	public List<DepartmentDto> findAllDepartmentByIds(List<Long> deptIds) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<DepartmentDto> query = builder.createQuery(DepartmentDto.class);
		Root<Department> root = query.from(Department.class);
		query.where(root.get("deptId").in(deptIds));
		query.select(builder.construct(DepartmentDto.class, root.get("deptId"), root.get("departmentName")));
		return manager.createQuery(query).getResultList();
	}

}
