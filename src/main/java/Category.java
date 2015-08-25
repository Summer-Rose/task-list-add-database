import java.util.List;
import org.sql2o.*;

public class Category {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Category(String name) {
    this.name = name;
  }

  public static List<Category> all() {
    String sql = "SELECT id, name FROM categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  @Override
  public boolean equals(Object otherCategory){
    if (!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategory = (Category) otherCategory;
      return this.getName().equals(newCategory.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static void removeCategory(int categoryid) {
    try (Connection con = DB.sql2o.open()) {
      String deleteTasks = "DELETE FROM tasks WHERE categoryid = :categoryid;";
      con.createQuery(deleteTasks) //grab all tasks in specified category
        .addParameter("categoryid", categoryid)
        .executeUpdate();
      String deleteCategory = "DELETE FROM categories WHERE id = :categoryid;";
      con.createQuery(deleteCategory)
        .addParameter("categoryid", categoryid)
        .executeUpdate();
    }
  }

  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM categories where id=:id";
      Category Category = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Category.class);
      return Category;
    }
  }

  public List<Task> getTasks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks WHERE categoryid=:id";
      return con.createQuery(sql)
      .addParameter("id", this.id)
      .executeAndFetch(Task.class);
   }
  }
}
