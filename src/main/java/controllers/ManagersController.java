package controllers;

import db.DBHelper;
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

public class ManagersController {

    public ManagersController() {
        this.setupEndPoints();


    }

    public void setupEndPoints() {

//        index
        get("/managers", (req, res) -> {

            Map<String, Object> model = new HashMap();
            model.put("template", "templates/managers/index.vtl");

            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

//      new
        get("/managers/new", (req, res) -> {

            Map<String, Object> model = new HashMap();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/managers/create.vtl");

            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

//      create
        post("/managers", (req, res) -> {

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);

            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            double budget = Double.parseDouble(req.queryParams("budget"));

            Manager newManager = new Manager(firstName, lastName, salary, department, budget);
            DBHelper.save(newManager);

            res.redirect("/managers");

            return null;
        }, new VelocityTemplateEngine());

//      show
        get("/managers/:id", (request, response) -> {

            Map<String, Object> model = new HashMap<>();

            model.put("template", "templates/managers/show.vtl");
            int managerId = Integer.parseInt(request.params(":id"));
            Manager manager = DBHelper.find(managerId, Manager.class);
            model.put("manager", manager);
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

//      edit
        get("/managers/:id/edit", (request, response) -> {

            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/edit.vtl");
            int managerId = Integer.parseInt(request.params(":id"));
            Manager manager = DBHelper.find(managerId, Manager.class);
            model.put("manager", manager);
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());


//      update

        post("/managers/:id", (req, res) -> {

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            int managerId = Integer.parseInt(req.params(":id"));
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            double budget = Double.parseDouble(req.queryParams("budget"));

            Manager manager = DBHelper.find(managerId, Manager.class);

            manager.setFirstName(firstName);
            manager.setLastName(lastName);
            manager.setSalary(salary);
            manager.setDepartment(department);
            manager.setBudget(budget);
            DBHelper.save(manager);

            res.redirect("/managers");

            return null;
        }, new VelocityTemplateEngine());


//      delete
        post("/managers/:id/delete", (req, res) -> {
            int managerId = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(managerId, Manager.class);
            DBHelper.delete(manager);

            res.redirect("/managers");

            return null;
        }, new VelocityTemplateEngine());

    }


}
