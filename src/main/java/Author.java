import java.util.List;
import org.sql2o.*;

public class Author {

  private int id;
  private String name;

  public Author(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  @Override
  public boolean equals(Object otherAuthor) {
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getName().equals(newAuthor.getName())
      && this.getId() == newAuthor.getId();
    }
  }

  public static List<Author> all() {
    String sql = "SELECT * FROM authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  public void save(){
    String sql = "INSERT INTO authors (name) VALUES (:name)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).executeUpdate().getKey();
    }
  }

  public static Author find(int id){
    String sql = "SELECT * FROM authors where id=:id";
    try(Connection con = DB.sql2o.open()) {
      Author author = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Author.class);
      return author;
    }
  }

  public void updateName(String newName){
    String sql = "UPDATE authors SET name = :newName WHERE id = :id ";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("newName", newName).addParameter("id", this.id).executeUpdate();
    }
  }

  public void delete(){
    String sql = "DELETE FROM authors WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

  public List<Book> getBooks(){
    try(Connection con = DB.sql2o.open()){
        String sql = "SELECT books.id, books.title FROM books JOIN authors_books ON (books.id = authors_books.book_id) JOIN authors ON (authors_books.author_id = authors.id) WHERE authors.id = :author_id;";
        List<Book> books = con.createQuery(sql).addParameter("author_id", this.getId()).executeAndFetch(Book.class);
        return books;
    }
  }
}
