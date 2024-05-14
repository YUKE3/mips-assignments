import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test35 {

  @Test
  public void verify_loot_hand_3() {
    Label args = asciiData(true, "L", "1P2P3P4P9P6P");
    run("hw_main", 2, args);
    Assert.assertEquals(-25, get(a0));
  }
}
