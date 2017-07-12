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


}
