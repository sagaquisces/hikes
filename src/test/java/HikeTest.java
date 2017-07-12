import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class HikeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  String testDescription = "This is a string that contains a long string as a placeholder for a textarea that will include the description of the hike for a details page. Let's make it long and full of &*%$# characters.";

  @Test
  public void Hike_instantiatesCorrectly_true() {
    Hike myHike = new Hike("Piss Point", testDescription, 1);
    assertEquals(true, myHike instanceof Hike);
  }

  @Test
  public void Hike_instantiatesWithName_true() {
    Hike myHike = new Hike("Piss Point", testDescription, 1);
    assertEquals("Piss Point", myHike.getName());
  }

  @Test
  public void Hike_instantiatesWithDescription_true() {
    Hike myHike = new Hike("Piss Point", testDescription, 1);
    assertEquals(testDescription, myHike.getDescription());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Hike firstHike = new Hike("Piss Point", testDescription, 1);
    Hike secondHike = new Hike("Piss Point", testDescription, 1);
    assertTrue(firstHike.equals(secondHike));
  }

  public static List<Hike> all() {
     String sql = "SELECT id, name, description, stateId FROM hikes";
     try(Connection con = DB.sql2o.open()) {
       return con.createQuery(sql).executeAndFetch(Hike.class);
     }
   }

  @Test
  public void save_assignsIdToObject() {
    Hike myHike = new Hike("Piss Point", testDescription, 1);
    myHike.save();
    Hike savedHike = Hike.all().get(0);
    assertEquals(myHike.getId(), savedHike.getId());
  }
}