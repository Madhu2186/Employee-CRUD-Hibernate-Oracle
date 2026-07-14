package com.employee.service;

import java.util.List;

import com.employee.dao.EmployeeDao;
import com.employee.entity.Employee;

// Connects the servlet and DAO layers, and validates data before saving.
public class EmployeeService {

	private EmployeeDao employeeDao = new EmployeeDao();

	public void addEmployee(Employee employee) {
		validateEmployee(employee);
		employeeDao.addEmployee(employee);
	}

	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	public Employee getEmployeeById(long id) {
		return employeeDao.getEmployeeById(id);
	}

	public void updateEmployee(Employee employee) {
		validateEmployee(employee);
		employeeDao.updateEmployee(employee);
	}

	public void deleteEmployee(long id) {
		employeeDao.deleteEmployee(id);
	}

	private void validateEmployee(Employee employee) {
		if (employee.getName() == null || employee.getName().trim().isEmpty()) {
			throw new IllegalArgumentException("Employee name is required");
		}

		if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
			throw new IllegalArgumentException("Email is required");
		}

		if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
			throw new IllegalArgumentException("Department is required");
		}

		if (employee.getSalary() == null || employee.getSalary().doubleValue() <= 0) {
			throw new IllegalArgumentException("Salary must be greater than zero");
		}

		if (employee.getJoinDate() == null) {
			throw new IllegalArgumentException("Join date is required");
		}
	}
}
