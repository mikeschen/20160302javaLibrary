import java.util.List;
import org.sql2o.*;

public class Patron{

	private int id;
	private String firstName;
	private String lastName;

	public Patron(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	//save method

	//all method
	public static List<Patron> all() {
		String sql = "SELECT id, first_name AS firstName, last_name AS lastName FROM patrons";
		try(Connection con = DB.sql2o.open()) {
		return con.createQuery(sql).executeAndFetch(Patron.class);
		}
	}

}