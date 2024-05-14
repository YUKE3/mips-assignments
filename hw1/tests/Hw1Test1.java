import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test1 {

  @Test
  public void verify_extra_args_err() {
    Label args = asciiData(true, "D", "123", "hello");
    run("hw_main", 3, args);
    Assert.assertEquals("Program requires exactly two arguments\n", getString(get(a0)));
  }
}
