# EmployeeModule
EmployeeModule to persist/retrieve data about employees and generating report about grouped salaries of employees
Used technologies: 
Database - H2 (lightweight, in-memory DB)
Hibernate
SpringBoot: 
  - Spring Data 
  - Spring Security 
    - two profiles: Dev(without authentication and crsf protection, Default)
  - Spring WEB (rest controllers): 
    - http://localhost:8087/employee/ - show all employees
    - http://localhost:8087/employee/{id} - show employee with {id}
    - http://localhost:8087/employee/salaryAtLeast/{salary} - show employees with salary at least {salary} 
    - http://localhost:8087/employee/add - POST request to add employee
JasperReports - used to generate report

Main responsobility beside retrieving data of employees is generating report which:
1) assigns unassigned employees to specific salary group (salary group is an entity having upper and lower bound of salary) or 
reassign employee in case when employee salary or his salary group  was updated (used technical lastModifiedDate for this purpose compared to lastModifiedDate of generated report) 
2) generates PDF file with table representing salary groups with quantity of employee assigned to them.
