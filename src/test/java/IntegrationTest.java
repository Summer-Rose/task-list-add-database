import org.fluentlenium.adapter.FluentTest;
import java.util.ArrayList;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("To Do List");
  }

  @Test
  public void displaysNewCategoryButton() {
    goTo("http://localhost:4567/");
    fill("#category").with("Java Class");
    submit(".btn");
    assertThat(pageSource()).contains("Java Class");
  }

  @Test
  public void displaysCategoryTaskAreaOnCategoryButtonClick() {
    Category testCategory = new Category("Java Class");
    testCategory.save();
    String categoryPath = String.format("http://localhost:4567/tasks/%d?categoryId=%d", testCategory.getId(), testCategory.getId());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Tasks in Java Class Category");
  }

  @Test
  public void newTaskDisplaysInTaskArea() {
    Category testCategory = new Category("Java Class");
    testCategory.save();
    Task testTask = new Task("Watch video", testCategory.getId());
    testTask.save();
    String categoryPath = String.format("http://localhost:4567/tasks/%d?categoryId=%d&description=%s", testCategory.getId(), testCategory.getId(), testTask.getDescription());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Watch video");
  }

  @Test
  public void editedTaskDisplaysCorrectly(){
    Category testCategory = new Category("Java Class");
    testCategory.save();
    Task testTask = new Task("Watch video", testCategory.getId());
    testTask.save();
    testTask.editTask(testTask.getId(), "Watch Treehouse");
    String categoryPath = String.format("http://localhost:4567/category/%d/edit/%d?description=%s", testCategory.getId(), testTask.getId(), testTask.getDescription());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Watch Treehouse");

  }


}
