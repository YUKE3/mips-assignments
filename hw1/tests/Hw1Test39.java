import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test39 {

  @Test
  public void verify_loot_inv_arg_4() {
    Label args = asciiData(true, "D", "1M2P3P4M9M6P");
    run("hw_main", 2, args);
    try {
        Assert.assertEquals("One of the arguments is invalid\n", getString(get(a0)));
    }
    catch(Exception e) {
      System.out.println("$a0 = " + String.valueOf(get(a0)));
      Assert.assertFalse("Expected an error message!", true);
    }
  }
}
