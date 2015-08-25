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
        //Task task = Task.find(Integer.parseInt(request.params(":id")));
        model.put("categories", Category.all());
        model.put("category", Category.find(Integer.parseInt(request.params(":id"))));
        model.put("template", "templates/index.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
    }
}
