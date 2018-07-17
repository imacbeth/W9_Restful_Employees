package controllers;


import db.DBHelper;
import db.Seeds;
import models.Department;
import models.Employee;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class EmployeesController {


    public EmployeesController(){
        this.setupEndPoints();
    }

        public void setupEndPoints() {

            get("/employees", (req, res) -> {

                Map<String, Object> model = new HashMap();
                model.put("template", "templates/employees/index.vtl");

                List<Employee> employees = DBHelper.getAll(Employee.class);
                model.put("employees", employees);
                return new ModelAndView(model, "templates/layout.vtl");

            }, new VelocityTemplateEngine());


            get("/employees/:id", (request, response) -> {

                Map<String, Object> model = new HashMap<>();

                model.put("template", "templates/employees/show.vtl");
                int employeeId = Integer.parseInt(request.params(":id"));
                Employee employee = DBHelper.find(employeeId, Employee.class);
                model.put("employee", employee);
                return new ModelAndView(model, "templates/layout.vtl");

            }, new VelocityTemplateEngine());


            get("/employees/:id/edit", (request, response) -> {

                Map<String, Object> model = new HashMap<>();
                model.put("template", "templates/employees/edit.vtl");
                int employeeId = Integer.parseInt(request.params(":id"));
                Employee employee = DBHelper.find(employeeId, Employee.class);
                model.put("employee", employee);
                List<Department> departments = DBHelper.getAll(Department.class);
                model.put("departments", departments);
                return new ModelAndView(model, "templates/layout.vtl");

            }, new VelocityTemplateEngine());



            post("/employees/:id", (req, res) -> {

                int departmentId = Integer.parseInt(req.queryParams("department"));
                Department department = DBHelper.find(departmentId, Department.class);
                int employeeId = Integer.parseInt(req.params(":id"));
                String firstName = req.queryParams("firstName");
                String lastName = req.queryParams("lastName");
                int salary = Integer.parseInt(req.queryParams("salary"));

                Employee employee = DBHelper.find(employeeId, Employee.class);


                employee.setFirstName(firstName);
                employee.setLastName(lastName);
                employee.setSalary(salary);
                employee.setDepartment(department);
                DBHelper.save(employee);

                res.redirect("/employees");

                return null;
            }, new VelocityTemplateEngine());

            post("/employees/:id/delete", (req, res) -> {
                int employeeId = Integer.parseInt(req.params(":id"));
                Employee employee = DBHelper.find(employeeId, Employee.class);
                DBHelper.delete(employee);

                res.redirect("/employees");

                return null;
            }, new VelocityTemplateEngine());





















        }

    }




