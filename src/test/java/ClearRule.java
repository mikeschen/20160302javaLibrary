import org.junit.rules.ExternalResource;

public class ClearRule extends ExternalResource {

  protected void before() { }

  protected void after() {
    //clear rules go here...
    //example: Definition.clear();
  }
}