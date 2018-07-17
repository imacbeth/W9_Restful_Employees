package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {

    public DepartmentsController(){
        this.setupEndPoints();
    }

    public void setupEndPoints() {

        get("/departments", (req, res) -> {

            Map<String, Object> model = new HashMap();
            model.put("template", "templates/departments/index.vtl");

            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());


        get("/departments/new", (req, res) -> {

            Map<String, Object> model = new HashMap();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/departments/create.vtl");


            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());


//
        post("/departments", (req, res) -> {

            String title = req.queryParams("title");

            Department newDepartment = new Department(title);
            DBHelper.save(newDepartment);

            res.redirect("/departments");

            return null;
        }, new VelocityTemplateEngine());
//

    }

}
