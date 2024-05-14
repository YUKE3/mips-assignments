import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test11 {

  @Test
  public void verify_convert_hex_to_decimal_2() {
    Label args = asciiData(true, "X", "0x5A2B");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", 23083, get(a0));
  }
}
