import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StateTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void State_instantiatesCorrectly_true() {
    State myState = new State("Washington");
    assertEquals(true, myState instanceof State);
  }

  @Test
  public void State_instantiatesWithName_true() {
    State myState = new State("Washington");
    assertEquals("Washington", myState.getName());
  }

  @Test
  public void save_assignsIdToObject() {
    State myState = new State("Washington");
    myState.save();
    State savedState = State.all().get(0);
    assertEquals(myState.getId(), savedState.getId());
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    State firstState = new State("Washington");
    State secondState = new State("Washington");
    assertTrue(firstState.equals(secondState));
  }

}
