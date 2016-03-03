import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;

public class PatronTest {

	@Rule
	public DatabaseRule database = new DatabaseRule();

	@Test
	public void instantiatesPatronObjectCorrectly_true(){
		Patron testPatron = new Patron("Omar","Ali");
		assertTrue(testPatron instanceof Patron);
	}

	@Test
	public void getFirstName_returnsFirstNamePropertyOfPatronObject_true(){
		Patron testPatron = new Patron("Omar","Ali");
		assertEquals("Omar", testPatron.getFirstName());
	}

	@Test
	public void getLastName_returnsFirstNamePropertyOfPatronObject_true(){
		Patron testPatron = new Patron("Omar","Ali");
		assertEquals("Ali", testPatron.getLastName());
	}

	@Test
  		public void all_emptyAtFirst() {
    	assertEquals(Patron.all().size(), 0);
  	}
}