import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test22 {

  @Test
  public void verify_convert_hex_to_float_3() {
    Label args = asciiData(true, "F", "F4483B47");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", 105, get(a0));
    Assert.assertEquals("Unexpected result", "-1.10010000011101101000111", getString(get(a1)));
  }
}
