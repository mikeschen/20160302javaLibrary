import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;

//Update Class name here and file name
public class AuthorTest {

	@Rule
	public DatabaseRule database = new DatabaseRule();

	@Test
	public void instatiatesAuthorObjectCorrectly_true(){
		Author testAuthor = new Author("JRR Tolkien");
		assertTrue(testAuthor instanceof Author);
	}

	@Test
	public void getName_returnsNamePropertyOfAuthorObject_true(){
    Author testAuthor = new Author("JRR Tolkien");
		assertEquals("JRR Tolkien", testAuthor.getName());
	}

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfAuthorsAndIdAretheSame() {
    Author firstAuthor = new Author("JRR Tolkien");
    Author secondAuthor = new Author("JRR Tolkien");
    assertTrue(firstAuthor.equals(secondAuthor));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Author testAuthor = new Author("JRR Tolkien");
    testAuthor.save();
    assertTrue(Author.all().get(0).equals(testAuthor));
  }

  @Test
  public void finds_findAuthorInDatabase_true() {
    Author testAuthor = new Author("JRR Tolkien");
    testAuthor.save();
    Author savedAuthor = Author.find(testAuthor.getId());
    assertTrue(testAuthor.equals(savedAuthor));
  }

  @Test
  public void updateName_updatesNamePropertyOfAuthorObject_true(){
    Author testAuthor = new Author("JRR Tolkien");
    testAuthor.save();
    testAuthor.updateName("Tolstoy");
    assertEquals(Author.all().get(0).getName(), "Tolstoy");
  }

  @Test
  public void deleteAuthor_deletesAuthorFromDB_true(){
    Author testAuthor = new Author("JRR Tolkien");
    testAuthor.save();
    assertEquals(Author.all().size(), 1);
    testAuthor.delete();
    assertEquals(Author.all().size(), 0);
  }

}
