import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    String layout = "templates/layout.vtl";

    //VIEW INDEX
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");

      model.put("books", Book.all());
      model.put("authors", Author.all());

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



    //CREATE AUTHOR OBJECT
    post("/author/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("authorName");
      Author newAuthor = new Author(name);
      newAuthor.save();
      response.redirect("/");
      return null;
    });

    //CREATE BOOK OBJECT
    post("/book/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("bookTitle");
      Book newBook = new Book(title);
      newBook.save();
      response.redirect("/");
      return null;
    });

    //VIEW INDIVIDUAL BOOK
    get("/book/:id", (request, response) -> {
			HashMap<String, Object> model = new HashMap<String, Object>();
			Book book = Book.find(Integer.parseInt(request.params(":id")));
			model.put("book", book);
      model.put("assignedAuthors", book.getAuthors());
      model.put("authors", Author.all());
			model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //UPDATE BOOK TITLE
    post("/book/:id/update-title", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      String newTitle = request.queryParams("newBookTitle");
      book.updateTitle(newTitle);

      response.redirect(String.format("/book/%d", book.getId()));

      return null;
    });

    //UPDATE BOOK AUTHOR
    // post("/book/:id/update-author", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Book book = Book.find(Integer.parseInt(request.params(":id")));
    //   String newAuthor = request.queryParams("newBookAuthor");
    //   book.updateAuthor(newAuthor);
    //
    //   response.redirect(String.format("/book/%d", book.getId()));
    //
    //   return null;
    // });

    //DELETE A BOOK
    post("/book/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      book.delete();
      response.redirect("/");
      return null;
    });

    //ASSIGN AUTHOR TO BOOK
    post("/book/:id/assign-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));

      int authorId = Integer.valueOf(request.queryParams("authorId"));

      book.assignToAuthor(authorId);

      String url = String.format("/book/%d", book.getId());
      response.redirect(url);
      return null;
    });

  }
}
