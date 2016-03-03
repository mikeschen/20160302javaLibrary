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
      model.put("patrons", Patron.all());

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

    //CREATE PATRON OBJECT
    post("/patron/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String firstName = request.queryParams("patronFirstName");
      String lastName = request.queryParams("patronLastName");
      Patron newPatron = new Patron(firstName, lastName);
      newPatron.save();
      response.redirect("/");
      return null;
    });

    //CREATE CHECKOUT OBJECT
    post("/book/:id/checkout/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Book book = Book.find(Integer.parseInt(request.params(":id")));
      int bookId = book.getId();

      int patronId = Integer.parseInt(request.queryParams("patronId"));

      Checkout checkout = new Checkout(patronId, bookId);
      checkout.save();

      model.put("book", book);

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
      model.put("copy", book.getCopyObject());
			model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //VIEW INDIVIDUAL AUTHOR
    get("/author/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Author author = Author.find(Integer.parseInt(request.params(":id")));
      model.put("author", author);
      model.put("books", author.getBooks());
      model.put("template", "templates/author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // VIEW INDIVIDUAL PATRON
    get("/patron/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Patron patron = Patron.find(Integer.parseInt(request.params("id")));
      model.put("patron", patron);
      model.put("template", "templates/patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //VIEW FORM TO CHECKOUT OUT A BOOK
    get("/book/:id/checkout", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      model.put("book", book);
      model.put("patrons", Patron.all());
      model.put("template", "templates/book-checkout-form.vtl");
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

    //CHECKOUT BOOK OBJECT
    // post("/book/:id/checkout", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Book book = Book.find(Integer.parseInt(request.params(":id")));
    //   book.checkOut();
    //   String url = String.format("/book/%d", book.getId());
    //   response.redirect(url);
    //   return null;
    // });

    //CHECKIN BOOK OBJECT
    post("/book/:id/checkin", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      book.checkIn();
      String url = String.format("/book/%d", book.getId());
      response.redirect(url);
      return null;
    });

    //CREATE NEW COPIES OBJECT
    post("/book/:id/copy/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      int count = Integer.parseInt(request.queryParams("copyCount"));
      Copy newCopy = new Copy(count);
      newCopy.save(book.getId());
      String url = String.format("/book/%d", book.getId());
      response.redirect(url);
      return null;
    });

  }
}
