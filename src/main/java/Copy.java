import java.util.*;
import org.sql2o.*;

public class Copy {
  private int id;
  private int count;

  public Copy(int count){
    this.count = count;
  }

  public int getId() {
    return id;
  }

  public int getCount() {
    return count;
  }

//needs to be tested eventually
  public void save(int bookId) {
    String sql = "INSERT INTO copies (count, book_id) VALUES (:count, :bookId)";
    try (Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true).addParameter("count", this.count).addParameter("bookId", bookId).executeUpdate().getKey();
    }
  }

}
