import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class StateTest {
  String testDescription = "This is a string that contains a long string as a placeholder for a textarea that will include the description of the hike for a details page. Let's make it long and full of &*%$# characters.";


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

  @Test
  public void getHikes_initiallyReturnsEmptyList_ArrayList() {
    State testState = new State("Washington");
    assertEquals(0, testState.getHikes().size());
  }

  @Test
  public void getHikes_retrievesAllHikesFromDatabase_hikesList() {
    State myState = new State("Washington");
    myState.save();
    Hike firstHike = new Hike("Piss Point", testDescription, myState.getId());
    firstHike.save();
    Hike secondHike = new Hike("Pupu Point", testDescription, myState.getId());
    secondHike.save();
    Hike[] hikes = new Hike[] { firstHike, secondHike };
    assertTrue(myState.getHikes().containsAll(Arrays.asList(hikes)));
  }

}
