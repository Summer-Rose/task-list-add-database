import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private int categoryId;
  private String description;

  public int getId() {
    return id;
  }

  public int getCategoryId(){
    return categoryId;
  }

  public String getDescription() {
    return description;
  }

  public Task(String description, int categoryId) {
    this.description = description;
    this.categoryId = categoryId;
  }

  @Override
  public boolean equals(Object otherTask) {
    if(!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId() &&
             this.getCategoryId() == newTask.getCategoryId();
    }
  }

  public static List<Task> all() {
    String sql = "SELECT id, description, categoryid FROM tasks";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public static List<Task> getTasksByCategoryId(int categoryId) {
    String sql = "SELECT * FROM tasks WHERE categoryid=" + categoryId;
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (description, categoryid) VALUES (:description, :categoryid)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("description", this.description)
      .addParameter("categoryid", this.categoryId)
      .executeUpdate()
      .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks WHERE id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }
}
