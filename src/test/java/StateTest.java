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

  @Test
  public void all_returnsAllInstancesOfState_true() {
    State firstState = new State("Washington");
    firstState.save();
    State secondState = new State("Oregon");
    secondState.save();
    assertEquals(true, State.all().get(0).equals(firstState));
    assertEquals(true, State.all().get(1).equals(secondState));
  }

  @Test
  public void find_returnsStateWithSameId_secondState() {
    State firstState = new State("Washington");
    firstState.save();
    State secondState = new State("Oregon");
    secondState.save();
    assertEquals(State.find(secondState.getId()), secondState);
  }

  @Test
  public void findDuplicate_returnsIDIfStateNamesAreSame() {
    State testState = new State("Washington");
    testState.save();
    String testString = "Washington";
    assertEquals(State.all().get(0).getId(), testState.findDuplicate("Washington"));
  }

}
