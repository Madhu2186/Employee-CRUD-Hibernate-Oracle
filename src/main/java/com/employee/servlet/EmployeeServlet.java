package com.employee.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.employee.entity.Employee;
import com.employee.service.EmployeeService;
import com.employee.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

	private EmployeeService employeeService = new EmployeeService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null) {
			action = "list";
		}

		try {
			if (action.equals("new")) {
				showAddForm(request, response);
			} else if (action.equals("edit")) {
				showEditForm(request, response);
			} else if (action.equals("delete")) {
				deleteEmployee(request, response);
			} else {
				listEmployees(request, response);
			}
		} catch (Exception exception) {
			request.setAttribute("errorMessage", exception.getMessage());
			listEmployees(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("update".equals(action)) {
			updateEmployee(request, response);
		} else {
			insertEmployee(request, response);
		}
	}

	private void listEmployees(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Employee> employees = employeeService.getAllEmployees();
		request.setAttribute("employees", employees);
		request.getRequestDispatcher("/WEB-INF/views/employee-list.jsp").forward(request, response);
	}

	private void showAddForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/employee-form.jsp").forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = Long.parseLong(request.getParameter("id"));
		Employee employee = employeeService.getEmployeeById(id);

		request.setAttribute("employee", employee);
		request.getRequestDispatcher("/WEB-INF/views/employee-form.jsp").forward(request, response);
	}

	private void insertEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Employee employee = createEmployeeFromRequest(request);
			employeeService.addEmployee(employee);

			request.getSession().setAttribute("message", "Employee added successfully");
			response.sendRedirect(request.getContextPath() + "/employees");
		} catch (Exception exception) {
			request.setAttribute("errorMessage", exception.getMessage());
			request.setAttribute("employee", createEmployeeForForm(request));
			request.getRequestDispatcher("/WEB-INF/views/employee-form.jsp").forward(request, response);
		}
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Employee employee = createEmployeeFromRequest(request);
			long id = Long.parseLong(request.getParameter("id"));
			employee.setId(id);

			employeeService.updateEmployee(employee);

			request.getSession().setAttribute("message", "Employee updated successfully");
			response.sendRedirect(request.getContextPath() + "/employees");
		} catch (Exception exception) {
			request.setAttribute("errorMessage", exception.getMessage());
			request.setAttribute("employee", createEmployeeForForm(request));
			request.getRequestDispatcher("/WEB-INF/views/employee-form.jsp").forward(request, response);
		}
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {

		long id = Long.parseLong(request.getParameter("id"));
		employeeService.deleteEmployee(id);

		request.getSession().setAttribute("message", "Employee deleted successfully");
		response.sendRedirect(request.getContextPath() + "/employees");
	}

	private Employee createEmployeeFromRequest(HttpServletRequest request) {
		Employee employee = new Employee();
		employee.setName(request.getParameter("name"));
		employee.setEmail(request.getParameter("email"));
		employee.setDepartment(request.getParameter("department"));
		employee.setSalary(new BigDecimal(request.getParameter("salary")));
		employee.setJoinDate(LocalDate.parse(request.getParameter("joinDate")));
		return employee;
	}

	private Employee createEmployeeForForm(HttpServletRequest request) {
		Employee employee = new Employee();

		String id = request.getParameter("id");
		if (id != null && !id.isEmpty()) {
			employee.setId(Long.parseLong(id));
		}

		employee.setName(request.getParameter("name"));
		employee.setEmail(request.getParameter("email"));
		employee.setDepartment(request.getParameter("department"));

		String salary = request.getParameter("salary");
		if (salary != null && !salary.isEmpty()) {
			try {
				employee.setSalary(new BigDecimal(salary));
			} catch (NumberFormatException exception) {
				// keep the form open even when the salary is invalid
			}
		}

		String joinDate = request.getParameter("joinDate");
		if (joinDate != null && !joinDate.isEmpty()) {
			employee.setJoinDate(LocalDate.parse(joinDate));
		}

		return employee;
	}

	@Override
	public void destroy() {
		HibernateUtil.shutdown();
	}
}
