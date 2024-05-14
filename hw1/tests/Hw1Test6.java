import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test6 {

  @Test
  public void verify_arg1_is_invalid() {
    Label args = asciiData(true, "Y", "123");
    run("hw_main", 2, args);
    try {
        Assert.assertEquals("One of the arguments is invalid\n", getString(get(a0)));
    }
    catch(Exception e) {
      Assert.assertFalse("Expected an error message!", true);
    }
  }
}
