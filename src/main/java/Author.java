import java.util.*;
import org.sql2o.*;

public class Author {

  //MEMBER VARIABLES
  private int id;
  private String name;

  //CONSTRUCTOR
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

}
