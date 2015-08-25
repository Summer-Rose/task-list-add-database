import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;


public class App {
    public static void main(String[] args) {
    	staticFileLocation("/public");
    	String layout = "templates/layout.vtl";

      get("/", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("categories", Category.all());
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/to-do-list", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        //save info from form entry
        String categoryName = request.queryParams("category");
        Category newCategory = new Category(categoryName);
        newCategory.save();
        model.put("categories", Category.all());
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/tasks/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        //fetch info from db
        String description = request.queryParams("description");
        int categoryId = Integer.parseInt(request.queryParams("categoryId"));
        Task newTask = new Task(description, categoryId);
        if (description != null){
          newTask.save();
        }

        model.put("categories", Category.all()); //displays category buttons
        model.put("tasks", Task.getTasksByCategoryId(categoryId));
        model.put("category", Category.find(Integer.parseInt(request.params(":id"))));
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/categories/:category_id/delete/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        int taskId = Integer.parseInt(request.params(":id"));
        Task.removeTaskById(taskId);

        Integer categoryId = Integer.parseInt(request.params(":category_id"));
        System.out.println(categoryId);

        //tasks not appearing when task is deleted on delete page
        //model.put("category", categoryId);
        model.put("categories", Category.all()); //buttons
        model.put("category", Category.find(categoryId));
        model.put("tasks", Task.getTasksByCategoryId(categoryId));
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/delete/:categoryid", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        int categoryid = Integer.parseInt(request.params(":categoryid"));
        Category.removeCategory(categoryid);

        //tasks not appearing when task is deleted on delete page
        //model.put("category", categoryId);
        model.put("categories", Category.all()); //buttons
        model.put("category", Category.find(categoryid));
        model.put("tasks", Task.getTasksByCategoryId(categoryid));
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/edit/:category_id/:description/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();

        //Task.find()

        Integer categoryid = Integer.parseInt(request.params(":category_id"));
        //String description = request.params(":description");
        Task editTask = Task.find(Integer.parseInt(request.params(":id")));

        model.put("editTask", editTask);
        //model.put("task", Task.find(editTask.getId()));
        model.put("category", Category.find(categoryid));
        model.put("tasks", Task.getTasksByCategoryId(categoryid));
        model.put("categories", Category.all()); //buttons
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/category/:category_id/edit/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Integer categoryid = Integer.parseInt(request.params(":category_id"));
        String description = request.queryParams("description");
        Integer taskId = Integer.parseInt(request.params(":id"));
        Task.editTask(taskId, description);
        model.put("category", Category.find(categoryid));
        model.put("tasks", Task.getTasksByCategoryId(categoryid));
        model.put("categories", Category.all()); //buttons
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
    }
}
