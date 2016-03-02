import java.util.*;
import org.sql2o.*;

public class Book{
  private int id;
  private String title;
  private String author;

  public Book (String title, String author){
    this.title = title;
    this.author = author;
  }

  public String getTitle(){
    return title;
  }

  public String getAuthor(){
    return author;
  }

  public int getId(){
    return id;
  }

  @Override
  public boolean equals(Object otherBook) {
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getAuthor().equals(newBook.getAuthor()) && this.getTitle().equals(newBook.getTitle()) && this.getId() == newBook.getId();
    }
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public void save(){
    String sql = "INSERT INTO books (title, author) VALUES (:title, :author)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("title", this.title).addParameter("author", this.author).executeUpdate().getKey();
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

  public void updateAuthor(String newAuthor){
    String sql = "UPDATE books SET author = :newAuthor WHERE id = :id ";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("newAuthor", newAuthor).addParameter("id", this.getId()).executeUpdate();
    }
  }

  public void delete(){
    String sql = "DELETE FROM books WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }
}
