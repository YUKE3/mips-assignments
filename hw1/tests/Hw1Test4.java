import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test4 {

  @Test
  public void verify_decimal_valid_single_digit() {
    Label args = asciiData(true, "D", "1");
    run("hw_main", 2, args);
    Assert.assertEquals(1, get(a0));
  }
}
