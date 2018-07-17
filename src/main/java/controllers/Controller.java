package controllers;

import db.DBHelper;
import db.Seeds;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class Controller {


    public static void main(String[] args) {

        Seeds.seedData();


        ManagersController managersController = new ManagersController();
        EmployeesController employeesController = new EmployeesController();
        DepartmentsController departmentsController = new DepartmentsController();


    }
}
