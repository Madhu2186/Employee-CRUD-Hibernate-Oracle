# Beginner Employee CRUD Project (Servlet + JSP + Hibernate + Oracle)

A simple Employee CRUD app used to teach Hibernate basics.

## Flow

```text
Browser -> EmployeeServlet -> EmployeeService -> EmployeeDao -> Hibernate -> Oracle
```

```text
com.employee.entity   Employee.java        Java class mapped to the EMPLOYEES table
com.employee.util     HibernateUtil.java   Builds one SessionFactory for the app
com.employee.dao      EmployeeDao.java     Only class that calls Hibernate directly
com.employee.service  EmployeeService.java Validates data, then calls the DAO
com.employee.servlet  EmployeeServlet.java Handles HTTP requests
```

## Main URLs

```text
/employees
/employees?action=new
/employees?action=edit&id=1
/employees?action=delete&id=1
```

## Software required

- Java 17
- Apache Maven
- Apache Tomcat 10.1 or later
- Oracle Database

## Database setup

1. Run `src/main/resources/schema.sql` in Oracle.
2. Update the connection URL/username/password in `src/main/resources/hibernate.cfg.xml`.

## Build

```bash
mvn clean package
```

Deploy the generated `employee-crud.war` file in Tomcat.
