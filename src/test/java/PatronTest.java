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

	@Test
		public void equals_returnsTrueIfFirstNameAndLastNameAretheSame() {
		Patron firstPatron = new Patron("Omar","Ali");
		Patron secondPatron = new Patron("Omar","Ali");
		assertTrue(firstPatron.equals(secondPatron));
	}

	@Test
		public void save_savesPatronObjectIntoDatabase_true() {
		Patron testPatron = new Patron("Omar","Ali");
		testPatron.save();
		assertTrue(Patron.all().get(0).equals(testPatron));
	}
}