import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test31 {

  @Test
  public void verify_convert_hex_to_float_inv_arg_2() {
    Label args = asciiData(true, "F", "FF8000012");
    run("hw_main", 2, args);
    try {
        Assert.assertEquals("One of the arguments is invalid\n", getString(get(a0)));
    }
    catch(Exception e) {
      System.out.println("$a0 = " + String.valueOf(get(a0)));
      Assert.assertFalse("Expected an error message as arg has more than 8 chars!", true);
    }
  }
}
