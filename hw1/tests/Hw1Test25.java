import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test25 {

  @Test
  public void verify_convert_hex_to_Zero_2() {
    Label args = asciiData(true, "F", "80000000");
    run("hw_main", 2, args);
    Assert.assertEquals("Unexpected result", "Zero\n", getString(get(a0)));
  }
}
