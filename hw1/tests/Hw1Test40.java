import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test40 {

  @Test
  public void verify_loot_inv_arg_5() {
    Label args = asciiData(true, "L", "1M2P3P4M9M6P", "M");
    run("hw_main", 3, args);
    try {
        Assert.assertEquals("Program requires exactly two arguments\n", getString(get(a0)));
    }
    catch(Exception e) {
      System.out.println("$a0 = " + String.valueOf(get(a0)));
      Assert.assertFalse("Expected an error message!", true);
    }
  }
}
