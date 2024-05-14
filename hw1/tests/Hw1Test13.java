import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test13 {

  @Test
  public void verify_convert_hex_to_decimal_4() {
    Label args = asciiData(true, "X", "0xFCD7A1B");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", 265124379, get(a0));
  }
}
