import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test26 {

  @Test
  public void verify_convert_hex_to_Inf_1() {
    Label args = asciiData(true, "F", "FF800000");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", "-Inf\n", getString(get(a0)));
  }
}
