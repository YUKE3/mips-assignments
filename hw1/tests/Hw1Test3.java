import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test3 {

  @Test
  public void verify_decimal_valid_multi_digits() {
    Label args = asciiData(true, "D", "123");
    run("hw_main", 2, args);
    Assert.assertEquals(123, get(a0));
  }
}
