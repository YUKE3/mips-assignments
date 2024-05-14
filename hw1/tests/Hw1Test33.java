import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test33 {

  @Test
  public void verify_loot_hand_1() {
    Label args = asciiData(true, "L", "1M2P3M4M9M6M");
    run("hw_main", 2, args);
    Assert.assertEquals(21, get(a0));
  }
}
