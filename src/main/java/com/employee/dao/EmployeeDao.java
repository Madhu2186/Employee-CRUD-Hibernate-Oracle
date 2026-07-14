package com.employee.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.employee.entity.Employee;
import com.employee.util.HibernateUtil;

// All database operations for Employee.
public class EmployeeDao {

	public void addEmployee(Employee employee) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			session.persist(employee);

			transaction.commit();
		} catch (RuntimeException exception) {
			if (transaction != null) {
				transaction.rollback();
			}
			exception.printStackTrace();
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public List<Employee> getAllEmployees() {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String hql = "from Employee order by id";
			return session.createQuery(hql, Employee.class).list();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Employee getEmployeeById(long id) {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			return session.get(Employee.class, id);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void updateEmployee(Employee employee) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			session.merge(employee);

			transaction.commit();
		} catch (RuntimeException exception) {
			if (transaction != null) {
				transaction.rollback();
			}
			exception.printStackTrace();
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void deleteEmployee(long id) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			Employee employee = session.get(Employee.class, id);
			if (employee != null) {
				session.remove(employee);
			}

			transaction.commit();
		} catch (RuntimeException exception) {
			if (transaction != null) {
				transaction.rollback();
			}
			exception.printStackTrace();
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
