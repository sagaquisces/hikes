import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class State {
  private String name;
  private int id;

  public State (String name) {
    this.name = name;

  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<State> all() {
    String sql = "SELECT id, name FROM states";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(State.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO states(name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static State find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM states where id=:id";
      State state = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(State.class);
      return state;
    }
  }

  @Override
  public boolean equals(Object otherState){
    if (!(otherState instanceof State)) {
      return false;
    } else {
      State newState = (State) otherState;
      return this.getName().equals(newState.getName()) &&
             this.getId() == newState.getId();
    }
  }

  public static int findDuplicate (String string) {
    List<State> allStates = State.all();
    int matchedId = -1;
    for (State temp : allStates) {
			if (string.equals(temp.getName())) {
        matchedId = temp.getId();
      }
		}
    return matchedId;
  }


}
