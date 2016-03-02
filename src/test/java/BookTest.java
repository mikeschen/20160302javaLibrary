import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;

//Update Class name here and file name
public class BookTest {

	@Rule
	public DatabaseRule database = new DatabaseRule();

	@Test
	public void instatiatesBookObjectCorrectly_true(){
		Book testBook = new Book("Lord of the Rings");
		assertTrue(testBook instanceof Book);
	}

	@Test
	public void getTitle_returnsTitlePropertyOfBookObject_true(){
		Book testBook = new Book("Lord of the Rings");
		assertEquals("Lord of the Rings", testBook.getTitle());
	}

	@Test
	public void all_emptyAtFirst() {
		assertEquals(Book.all().size(), 0);
	}

	@Test
  public void equals_returnsTrueIfAuthorsAndIdAretheSame() {
		Book firstBook = new Book("Lord of the Rings");
		Book secondBook = new Book("Lord of the Rings");
    assertTrue(firstBook.equals(secondBook));
  }

	@Test
	public void save_savesIntoDatabase_true() {
		Book testBook = new Book("Lord of the Rings");
		testBook.save();
		assertTrue(Book.all().get(0).equals(testBook));
	}

	@Test
	public void finds_findBookInDatabase_true() {
		Book testBook = new Book("Lord of the Rings");
		testBook.save();
		Book savedBook = Book.find(testBook.getId());
		assertTrue(testBook.equals(savedBook));
	}

	@Test
	public void updateTitle_updatesTitlePropertyOfBookObject_true(){
		Book testBook = new Book("Lord of the Rings");
		testBook.save();
		testBook.updateTitle("Return of the King");
		assertEquals(Book.all().get(0).getTitle(), "Return of the King");
	}

	@Test
	public void deleteBook_deletesBook_true(){
		Book testBook = new Book("Lord of the Rings");
		testBook.save();
		assertEquals(Book.all().size(), 1);
		testBook.delete();
		assertEquals(Book.all().size(), 0);
	}

	@Test
	public void assignToAuthor_createsRecordInAuthorsBooksTable_true(){
		Book testBook = new Book("Lord of the Rings");
		testBook.save();
		Author testAuthor = new Author("JRR Tolkien");
		testAuthor.save();

		testBook.assignToAuthor(testAuthor.getId());
		Author savedAuthor = testBook.getAuthors().get(0);
		assertTrue(testAuthor.equals(savedAuthor));
	}

	//test to check out a book
	@Test
	public void checkOut_changeValueOfIsCheckedOutPropertyFromFalseToTrue_true(){
		Book testBook = new Book("Lord of the Rings");
    testBook.save();
		testBook.checkOut();
		assertEquals(Book.find(testBook.getId()).getIsCheckedOut(), true);
	}

	@Test
	public void checkIn_changeValueOfIsCheckedOutPropertyFromTrueToFalse_true(){
		Book testBook = new Book("Lord of the Rings");
		testBook.save();
		testBook.checkOut();
		testBook.checkIn();
		assertEquals(Book.find(testBook.getId()).getIsCheckedOut(), false);
	}

}
