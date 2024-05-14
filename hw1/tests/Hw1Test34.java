import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw1Test34 {

  @Test
  public void verify_loot_hand_2() {
    Label args = asciiData(true, "L", "1M2P3M4M9M6P");
    run("hw_main", 2, args);
    Assert.assertEquals(9, get(a0));
  }
}
