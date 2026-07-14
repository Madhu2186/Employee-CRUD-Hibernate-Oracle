<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee List</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
<div class="container">
    <div class="page-header">
        <div>
            <h1>Employee Management</h1>
            <p>Servlet, JSP, Hibernate and Oracle CRUD project</p>
        </div>
        <a class="button primary" href="${pageContext.request.contextPath}/employees?action=new">
            Add Employee
        </a>
    </div>

    <c:if test="${not empty sessionScope.message}">
        <div class="alert success">${sessionScope.message}</div>
        <c:remove var="message" scope="session" />
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert error">${errorMessage}</div>
    </c:if>

    <div class="table-card">
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Salary</th>
                <th>Join Date</th>
                <th>Action</th>
            </tr>

            <c:forEach var="employee" items="${employees}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.name}</td>
                    <td>${employee.email}</td>
                    <td>${employee.department}</td>
                    <td>${employee.salary}</td>
                    <td>${employee.joinDate}</td>
                    <td class="actions">
                        <a class="button small"
                           href="${pageContext.request.contextPath}/employees?action=edit&id=${employee.id}">
                            Edit
                        </a>
                        <a class="button small danger"
                           href="${pageContext.request.contextPath}/employees?action=delete&id=${employee.id}"
                           onclick="return confirm('Do you want to delete this employee?')">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty employees}">
                <tr>
                    <td colspan="7" class="empty">No employee records found</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>
</body>
</html>
