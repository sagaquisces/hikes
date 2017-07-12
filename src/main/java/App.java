import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/hikes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("hikes", Hike.all());
      model.put("template", "templates/hikes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/hikes/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Hike hike = Hike.find(Integer.parseInt(request.params(":id")));
      model.put("hike", hike);
      model.put("template", "templates/hike.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/new-hike", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/hike-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/hikes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      Hike newHike = new Hike(name, description, 1);
      newHike.save();
      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
