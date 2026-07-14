<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Form</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
<div class="container narrow">
    <div class="form-card">

        <h1>${employee.id != null ? 'Edit Employee' : 'Add Employee'}</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/employees">

            <input type="hidden" name="action" value="${employee.id != null ? 'update' : 'insert'}">
            <c:if test="${employee.id != null}">
                <input type="hidden" name="id" value="${employee.id}">
            </c:if>

            <label>Employee Name</label>
            <input type="text" name="name" value="${employee.name}" required>

            <label>Email</label>
            <input type="email" name="email" value="${employee.email}" required>

            <label>Department</label>
            <input type="text" name="department" value="${employee.department}" required>

            <label>Salary</label>
            <input type="number" name="salary" step="0.01" value="${employee.salary}" required>

            <label>Join Date</label>
            <input type="date" name="joinDate" value="${employee.joinDate}" required>

            <div class="form-actions">
                <button class="button primary" type="submit">Save</button>
                <a class="button" href="${pageContext.request.contextPath}/employees">Cancel</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
