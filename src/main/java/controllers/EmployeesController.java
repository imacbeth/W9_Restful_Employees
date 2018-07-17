package controllers;


import db.DBHelper;
import db.Seeds;
import models.Department;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class EmployeesController {


    public EmployeesController(){
        this.setupEndPoints();
    }

        public void setupEndPoints() {

            // when you do this first time you gotta click on the red light bulb that comes and select the
            // option that makes this accept lambdas
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

        }

    }




