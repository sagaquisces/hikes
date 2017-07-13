import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Hike {
  private String name;
  private String description;
  private int id;
  private int stateId;

  public Hike(String name, String description, int stateId) {
    this.name = name;
    this.description = description;
    this.stateId = stateId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getId()  {
    return id;
  }

  public int getStateId() {
    return stateId;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO hikes(stateId, name, description) VALUES (:stateId, :name, :description);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("stateId", this.stateId)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherHike){
    if (!(otherHike instanceof Hike)) {
      return false;
    } else {
      Hike newHike = (Hike) otherHike;
      return this.getDescription().equals(newHike.getDescription()) &&
             this.getId() == newHike.getId() &&
             this.getName().equals(newHike.getName());
            //  this.getStateId() == newHike.getStateId();
    }
  }

  public static List<Hike> all() {
    String sql = "SELECT id, stateId, name, description FROM hikes";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Hike.class);
    }
  }

  public static Hike find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM hikes where id=:id";
      Hike hike = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Hike.class);
      return hike;
    }
  }

  // public String getStateName(int id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT name FROM states where id=:id";
  //     String stateName = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetchFirst(State.class);
  //
  //   }
  // }

}
