import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test20 {

  @Test
  public void verify_convert_hex_to_float_1() {
    Label args = asciiData(true, "F", "42864000");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", 6, get(a0));
    Assert.assertEquals("Unexpected result", "1.00001100100000000000000", getString(get(a1)));
  }
}
