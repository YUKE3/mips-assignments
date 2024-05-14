import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test19 {

  @Test
  public void verify_convert_hex_to_decimal_inv_arg_4() {
    Label args = asciiData(true, "XFC", "0XFCD7A1");
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
