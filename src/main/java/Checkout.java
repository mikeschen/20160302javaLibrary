import java.util.*;
import org.sql2o.*;

public class Checkout {
  private int id;
  private int patronId;
  private int bookId;
  private Date checkoutDate;

  public Checkout(int patronId, int bookId){
    this.id = id;
    this.patronId = patronId;
    this.bookId = bookId;
    this.checkoutDate = new Date();
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
    return checkoutDate;
  }

  //implement an equals method for Checkout
  @Override
  public boolean equals(Object otherCheckout) {
    if (!(otherCheckout instanceof Checkout)) {
      return false;
    } else {
      Checkout newCheckout = (Checkout) otherCheckout;
      return this.getId() == newCheckout.getId();
    }
  }

  //implement a save method for Checkout
  public void save(){
    String sql = "INSERT INTO checkouts (patron_id, book_id, checkout_date) VALUES (:patronId, :bookId, :checkoutDate)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("patronId", this.patronId).addParameter("bookId", this.bookId).addParameter("checkoutDate", this.checkoutDate).executeUpdate().getKey();
    }
  }

  public static List<Checkout> all() {
    String sql = "SELECT id, patron_id AS patronId, book_id AS bookId, checkout_date AS checkoutDate FROM checkouts";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Checkout.class);
    }
  }

}
