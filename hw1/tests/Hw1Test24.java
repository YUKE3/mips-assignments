import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test24 {

  @Test
  public void verify_convert_hex_to_Zero_1() {
    Label args = asciiData(true, "F", "00000000");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", "Zero\n", getString(get(a0)));
  }
}
