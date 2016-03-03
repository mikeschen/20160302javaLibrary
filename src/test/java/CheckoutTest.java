import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.*;

public class CheckoutTest {

	@Rule
	public DatabaseRule database = new DatabaseRule();

	@Test
	public void instantiatesCheckoutObjectCorrectly_true(){
		Checkout testCheckout = new Checkout(1, 1);
		assertTrue(testCheckout instanceof Checkout);
	}

	@Test
  public void equals_returnsTrueIfPatronIdBookIdAndDateAretheSame() {
		Checkout firstCheckout = new Checkout(1, 1);
		Checkout secondCheckout = new Checkout(1, 1);
    assertTrue(firstCheckout.equals(secondCheckout));
  }

	@Test
	public void all_emptyAtFirst() {
		assertEquals(Checkout.all().size(), 0);
	}

	@Test
	public void all_OneElementAfterSave_true() {
		Checkout testCheckout = new Checkout(1, 1);
		testCheckout.save();
		assertEquals(Checkout.all().size(), 1);
	}

	@Test
  public void save_savesIntoDatabase_true() {
		Checkout testCheckout = new Checkout(1, 1);
    testCheckout.save();
    Checkout savedCheckout = Checkout.all().get(0);
		assertEquals(testCheckout.getId(), savedCheckout.getId());
  }

	@Test
	public void save_savesIntoDatabase2_true() {
		Checkout testCheckout = new Checkout(1, 1);
		testCheckout.save();
		assertTrue(Checkout.all().get(0).equals(testCheckout));
	}

}
