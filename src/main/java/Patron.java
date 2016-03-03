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

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public int getId(){
		return id;
	}

	public static List<Patron> all() {
		String sql = "SELECT id, first_name AS firstName, last_name AS lastName FROM patrons";
		try(Connection con = DB.sql2o.open()) {
		return con.createQuery(sql).executeAndFetch(Patron.class);
	    }
	}

  @Override
  public boolean equals(Object otherPatron) {
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getFirstName().equals(newPatron.getFirstName())
      && this.getLastName().equals(newPatron.getLastName())
      && this.getId() == newPatron.getId();
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
    	String sql = "INSERT INTO patrons (first_name, last_name) VALUES (:firstName, :lastName)";
    	this.id = (int) con.createQuery(sql, true).addParameter("firstName", this.getFirstName()).addParameter("lastName", this.getLastName()).executeUpdate().getKey();
    }
  }

	public static Patron find(int id){
		String sql = "SELECT id, first_name AS firstName, last_name AS lastName FROM patrons WHERE id=:id";
		// String sql = "SELECT id, first_name, last_name FROM patrons WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
			Patron patron = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Patron.class);
			return patron;
	  }
	}

}
