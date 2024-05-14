import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test12 {

  @Test
  public void verify_convert_hex_to_decimal_3() {
    Label args = asciiData(true, "X", "0xF8CD7A1B");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", -120751589, get(a0));
  }
}
