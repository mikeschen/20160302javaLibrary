import java.util.*;
import org.sql2o.*;

// As a patron, I want to check a book out, so that I can take it home with me.

//implement a property to keep track of checked out status
//implement a method to checkout a book which will change the checked out status
//implement a method to checkin a book which will change the checked out status

public class Book{
  private int id;
  private String title;
  private boolean isCheckedOut;

  public Book (String title){
    this.title = title;
  }

  public String getTitle(){
    return title;
  }

  public int getId(){
    return id;
  }

  public boolean getIsCheckedOut(){
    return isCheckedOut;
  }

  @Override
  public boolean equals(Object otherBook) {
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) && this.getId() == newBook.getId();
    }
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public void save(){
    String sql = "INSERT INTO books (title, isCheckedOut) VALUES (:title, :isCheckedOut)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("title", this.title).addParameter("isCheckedOut", this.isCheckedOut).executeUpdate().getKey();
    }
  }

  public static Book find(int id){
    String sql = "SELECT * FROM books where id=:id";
    try(Connection con = DB.sql2o.open()) {
      Book book = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void updateTitle(String newTitle){
    String sql = "UPDATE books SET title = :newTitle WHERE id = :id ";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("newTitle", newTitle).addParameter("id", this.getId()).executeUpdate();
    }
  }

  public void delete(){
    String sql = "DELETE FROM books WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

  public void assignToAuthor(int authorId){
    String sql = "INSERT INTO authors_books (author_id, book_id) VALUES (:author_id, :book_id);";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("author_id", authorId).addParameter("book_id", this.id).executeUpdate();
		}
  }

  //getAuthors method using JOINS
	public List<Author> getAuthors(){
		try(Connection con = DB.sql2o.open()){
			String sql = "SELECT authors.id, authors.name FROM authors JOIN authors_books ON (authors.id = authors_books.author_id) JOIN books ON (authors_books.book_id = books.id) WHERE books.id = :book_id;";
			List<Author> authors = con.createQuery(sql).addParameter("book_id", this.getId()).executeAndFetch(Author.class);
			return authors;
		}
	}

  //getCopyObject method using JOINS
  public Copy getCopyObject(){
    String sql = "SELECT id, count, book_id AS id FROM copies where book_id = :book_id";
    // String sql = "SELECT * FROM copies where book_id = :book_id";
    try(Connection con = DB.sql2o.open()){
      Copy copy = con.createQuery(sql).addParameter("book_id", this.id).executeAndFetchFirst(Copy.class);
      return copy;
    }
  }

  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "SELECT authors.id, authors.name FROM authors JOIN authors_books ON (authors.id = authors_books.author_id) JOIN books ON (authors_books.book_id = books.id) WHERE books.id = :book_id;";
  //     List<Author> authors = con.createQuery(sql).addParameter("book_id", this.getId()).executeAndFetch(Author.class);
  //     return authors;
  //   }
  // }

  public void checkOut() {
    try(Connection con = DB.sql2o.open()){
    String sql = "UPDATE copies SET count=count-1 WHERE book_id = :book_id";
    con.createQuery(sql).addParameter("book_id", id).executeUpdate();
    }
  }

  public void checkIn() {
    try(Connection con = DB.sql2o.open()){
    String sql = "UPDATE copies SET count=count+1 WHERE book_id = :book_id";
    con.createQuery(sql).addParameter("book_id", id).executeUpdate();
    }
  }

}
