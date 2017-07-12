import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hikes_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteHikesQuery = "DELETE FROM hikes *;";
      String deleteStatesQuery = "DELETE FROM states *;";
      con.createQuery(deleteHikesQuery).executeUpdate();
      con.createQuery(deleteStatesQuery).executeUpdate();
    }
  }

}
