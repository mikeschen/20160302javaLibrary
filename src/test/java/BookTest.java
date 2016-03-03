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
		Book testBook = new Book("Lord of the Rings", 1);
		assertTrue(testBook instanceof Book);
	}

	@Test
	public void getTitle_returnsTitlePropertyOfBookObject_true(){
		Book testBook = new Book("Lord of the Rings", 1);
		assertEquals("Lord of the Rings", testBook.getTitle());
	}

	@Test
	public void all_emptyAtFirst() {
		assertEquals(Book.all().size(), 0);
	}

	@Test
  public void equals_returnsTrueIfAuthorsAndIdAretheSame() {
		Book firstBook = new Book("Lord of the Rings", 1);
		Book secondBook = new Book("Lord of the Rings", 1);
    assertTrue(firstBook.equals(secondBook));
  }

	@Test
	public void save_savesIntoDatabase_true() {
		Book testBook = new Book("Lord of the Rings", 1);
		testBook.save();
		assertTrue(Book.all().get(0).equals(testBook));
	}

	@Test
	public void finds_findBookInDatabase_true() {
		Book testBook = new Book("Lord of the Rings", 1);
		testBook.save();
		Book savedBook = Book.find(testBook.getId());
		assertTrue(testBook.equals(savedBook));
	}

	@Test
	public void updateTitle_updatesTitlePropertyOfBookObject_true(){
		Book testBook = new Book("Lord of the Rings", 1);
		testBook.save();
		testBook.updateTitle("Return of the King");
		assertEquals(Book.all().get(0).getTitle(), "Return of the King");
	}

	@Test
	public void deleteBook_deletesBook_true(){
		Book testBook = new Book("Lord of the Rings", 1);
		testBook.save();
		assertEquals(Book.all().size(), 1);
		testBook.delete();
		assertEquals(Book.all().size(), 0);
	}

	@Test
	public void assignToAuthor_createsRecordInAuthorsBooksTable_true(){
		Book testBook = new Book("Lord of the Rings", 1);
		testBook.save();
		Author testAuthor = new Author("JRR Tolkien");
		testAuthor.save();

		testBook.assignToAuthor(testAuthor.getId());
		Author savedAuthor = testBook.getAuthors().get(0);
		assertTrue(testAuthor.equals(savedAuthor));
	}


}
