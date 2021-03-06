import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Checkout {
  private int id;
  private int patronId;
  private int bookId;
  private Date checkout_date;
  private Date due_date;
  private boolean returned;

  public Checkout(int patronId, int bookId){
    this.id = id;
    this.patronId = patronId;
    this.bookId = bookId;
    this.checkout_date = new Date();
    this.initDueDate();
  }

  public int getId(){
    return id;
  }

  public int getPatronId(){
    return patronId;
  }

  public int getBookId(){
    return bookId;
  }

  public Date getDate(){
    return checkout_date;
  }

  public Date getDueDate(){
    return due_date;
  }

  public boolean getReturned(){
    return returned;
  }

  public void initDueDate() {
      Date date = new Date();
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.add(Calendar.WEEK_OF_MONTH, 2);
      date = c.getTime();
      this.due_date = date;
  }

  //implement method to check if book is returned or not
  public boolean isReturned(){
    return returned;
  }

  @Override
  public boolean equals(Object otherCheckout) {
    if (!(otherCheckout instanceof Checkout)) {
      return false;
    } else {
      Checkout newCheckout = (Checkout) otherCheckout;
      return this.getId() == newCheckout.getId();
    }
  }

  public void save(){
    String sql = "INSERT INTO checkouts (patron_id, book_id, checkout_date, due_date, returned) VALUES (:patronId, :bookId, :checkout_date, :due_date, :returned)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("patronId", this.patronId).addParameter("bookId", this.bookId).addParameter("checkout_date", this.checkout_date).addParameter("due_date", this.due_date).addParameter("returned", this.returned).executeUpdate().getKey();
    }
    try(Connection con = DB.sql2o.open()){
      String copy = "UPDATE books SET copies=copies-1 WHERE id = :book_id";
      con.createQuery(copy).addParameter("book_id", this.bookId).executeUpdate();
    }
  }

  public void checkin(){
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE checkouts SET returned = true WHERE id=:id";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
      String sql2 = "UPDATE books SET copies=copies+1 WHERE id = :book_id";
      con.createQuery(sql2).
      addParameter("book_id", this.bookId)
      .executeUpdate();
    }
  }

  public static Checkout find(int id){
    String sql = "SELECT id, patron_id AS patronId, book_id AS bookId, checkout_date, due_date, returned FROM checkouts WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      Checkout checkout = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Checkout.class);
      return checkout;
    }
  }

  public static List<Checkout> all() {
    String sql = "SELECT id, patron_id AS patronId, book_id AS bookId, checkout_date FROM checkouts";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Checkout.class);
    }
  }

  public Book getBook(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT books.id, books.title, books.copies FROM books WHERE id = :bookId";
      Book book = con.createQuery(sql).addParameter("bookId", this.bookId).executeAndFetchFirst(Book.class);
      return book;
    }
  }
}
