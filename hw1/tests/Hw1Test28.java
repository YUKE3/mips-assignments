import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test28 {

  @Test
  public void verify_convert_hex_to_Nan_1() {
    Label args = asciiData(true, "F", "7F800008");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", "NaN\n", getString(get(a0)));
  }
}
