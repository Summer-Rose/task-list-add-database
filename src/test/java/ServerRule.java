import org.junit.rules.ExternalResource;
import spark.Spark;

public class ServerRule extends ExternalResource {

  protected void before() {
    String[] args = {};
    App.main(args); //might need to change name
  }

  protected void after() {
    Spark.stop();
  }
}
