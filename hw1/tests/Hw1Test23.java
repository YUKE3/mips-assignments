import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test23 {

  @Test
  public void verify_convert_hex_to_float_4() {
    Label args = asciiData(true, "F", "811234AC");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", -125, get(a0));
    Assert.assertEquals("Unexpected result", "-1.00100100011010010101100", getString(get(a1)));
  }
}
