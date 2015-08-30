import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Task firstTask = new Task("mow the lawn");
    Task secondTask = new Task("mow the lawn");
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Task myTask = new Task("mow the lawn");
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void save_assignsIdToObject() {
    Task myTask = new Task("mow the lawn");
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("mow the lawn");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  // @Test
  // public void save_savesCategoryIdIntoDB_true() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   Task myTask = new Task("mow the lawn", myCategory.getId());
  //   myTask.save();
  //   Task savedTask = Task.find(myTask.getId());
  //   assertEquals(savedTask.getCategoryId(), myCategory.getId());
  // }

  // @Test
  // public void remove_removesTaskById_true() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   Task myTask = new Task("feed fish", myCategory.getId());
  //   myTask.save();
  //   Task.removeTaskById(myTask.getId());
  //   assertTrue(Task.all().size() == 0);
  // }

  // @Test
  // public void edit_newTaskDescription() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   Task myTask = new Task("feed fish", myCategory.getId());
  //   myTask.save();
  //   Task editTask = new Task("feed cat", myCategory.getId());
  //   myTask.editTask(editTask.getId(), editTask.getDescription());
  //   assertTrue(editTask.getDescription() == "feed cat");
  // }

}
