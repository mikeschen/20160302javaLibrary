import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      //DELETE statements for each table in test DB
      // String deleteRestaurantsQuery = "DELETE FROM restaurants *;";
      // String deleteCuisineQuery = "DELETE FROM cuisine *;";
      // con.createQuery(deleteRestaurantsQuery).executeUpdate();
      // con.createQuery(deleteCuisineQuery).executeUpdate();
    }
  }
}
