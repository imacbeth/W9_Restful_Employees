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

    public ManagersController(){
        this.setupEndPoints();


    }

public void setupEndPoints(){

    get("/managers", (req, res) -> {

        Map<String, Object> model = new HashMap();
        model.put("template", "templates/managers/index.vtl");

        List<Manager> managers = DBHelper.getAll(Manager.class);
        model.put("managers", managers);
        return new ModelAndView(model, "templates/layout.vtl");

    }, new VelocityTemplateEngine());


    get("/managers/new", (req, res) -> {


        Map<String, Object> model = new HashMap();
        List<Department> departments = DBHelper.getAll(Department.class);
        model.put("departments", departments);
        model.put("template", "templates/managers/create.vtl");

//        List<Manager> managers = DBHelper.getAll(Manager.class);
//        model.put("managers", managers);
        return new ModelAndView(model, "templates/layout.vtl");

    }, new VelocityTemplateEngine());



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






}


}