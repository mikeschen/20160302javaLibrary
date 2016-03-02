import java.util.*;
import org.sql2o.*;

public class Book{
  private int id;
  private String title;

  public Book (String title){
    this.title = title;
  }

  public String getTitle(){
    return title;
  }

  // public String getAuthor(){
  //   return author;
  // }

  public int getId(){
    return id;
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
    String sql = "INSERT INTO books (title) VALUES (:title)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("title", this.title).executeUpdate().getKey();
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

  // public void updateAuthor(String newAuthor){
  //   String sql = "UPDATE books SET author = :newAuthor WHERE id = :id ";
  //   try(Connection con = DB.sql2o.open()){
  //     con.createQuery(sql).addParameter("newAuthor", newAuthor).addParameter("id", this.getId()).executeUpdate();
  //   }
  // }

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

  //getStudents method using JOINS
	// public List<Student> getStudents(){
	// 	try(Connection con = DB.sql2o.open()){
	// 		String sql = "SELECT students.id, students.name, students.date_of_enrollment AS dateOfEnrollment FROM courses JOIN students_courses ON (courses.id = students_courses.course_id) JOIN students ON (students_courses.student_id = students.id) WHERE courses.id = :course_id;";
	// 		List<Student> students = con.createQuery(sql).addParameter("course_id", this.getId()).executeAndFetch(Student.class);
	// 		return students;
	// 	}
	// }



}
