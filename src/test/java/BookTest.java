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
		Book testBook = new Book("Lord of the Rings", "JRR Tolkien");
		assertTrue(testBook instanceof Book);
	}

	@Test
	public void getTitle_returnsTitlePropertyOfBookObject_true(){
		Book testBook = new Book("Lord of the Rings", "JRR Tolkien");
		assertEquals("Lord of the Rings", testBook.getTitle());
	}

	@Test
	public void getAuthor_returnsAuthorPropertyOfBookObject_true(){
		Book testBook = new Book("Lord of the Rings", "JRR Tolkien");
		assertEquals("JRR Tolkien", testBook.getAuthor());
	}

	@Test
	public void all_emptyAtFirst() {
		assertEquals(Book.all().size(), 0);
	}

	@Test
  public void equals_returnsTrueIfAuthorsAndTitlesAretheSame() {
		Book firstBook = new Book("Lord of the Rings", "JRR Tolkien");
		Book secondBook = new Book("Lord of the Rings", "JRR Tolkien");
    assertTrue(firstBook.equals(secondBook));
  }

	@Test
	public void save_savesIntoDatabase_true() {
		Book testBook = new Book("Lord of the Rings", "JRR Tolkien");
		testBook.save();
		assertTrue(Book.all().get(0).equals(testBook));
	}

	@Test
	public void finds_findBookInDatabase_true() {
		Book testBook = new Book("Lord of the Rings", "JRR Tolkien");
		testBook.save();
		Book savedBook = Book.find(testBook.getId());
		assertTrue(testBook.equals(savedBook));
	}

	@Test
	public void updateTitle_updatesTitlePropertyOfBookObject_true(){
		Book testBook = new Book("Lord of the Rings", "JRR Tolkien");
		testBook.save();
		testBook.updateTitle("Return of the King");
		assertEquals(Book.all().get(0).getTitle(), "Return of the King");
	}

	@Test
	public void updateAuthor_updatesAuthorPropertyOfBookObject_true(){
		Book testBook = new Book("Lord of the Rings", "JRR Tolkien");
		testBook.save();
		testBook.updateAuthor("JK Rowling");
		assertEquals(Book.all().get(0).getAuthor(), "JK Rowling");
	}

	@Test
	public void deleteBook_deletesBook_true(){
		Book testBook = new Book("Lord of the Rings", "JRR Tolkien");
		testBook.save();
		assertEquals(Book.all().size(), 1);
		testBook.delete();
		assertEquals(Book.all().size(), 0);
	}

}