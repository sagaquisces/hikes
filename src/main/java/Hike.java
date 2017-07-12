import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Hike {
  private String name;
  private String description;
  private int id;
  private int stateId;

  public Hike(String name, String description, int id) {
    this.name = name;
    this.description = description;
    this.id = id;
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
}