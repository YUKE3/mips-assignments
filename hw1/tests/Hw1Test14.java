import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test14 {

  @Test
  public void verify_convert_hex_to_decimal_5() {
    Label args = asciiData(true, "X", "0xFFFFFFFF");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", -1, get(a0));
  }
}
