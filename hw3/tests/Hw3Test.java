import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;
import java.io.*;

public class Hw3Test {
  private int reg_sp = 0;
  private int reg_s0 = 0;
  private int reg_s1 = 0;
  private int reg_s2 = 0;
  private int reg_s3 = 0;
  private int reg_s4 = 0;
  private int reg_s5 = 0;
  private int reg_s6 = 0;
  private int reg_s7 = 0;

  @Before
  public void preTest() {
    this.reg_s0 = get(s0);
    this.reg_s1 = get(s1);
    this.reg_s2 = get(s2);
    this.reg_s3 = get(s3);
    this.reg_s4 = get(s4);
    this.reg_s5 = get(s5);
    this.reg_s6 = get(s6);
    this.reg_s7 = get(s7);
    this.reg_sp = get(sp);
  }

  @After
  public void postTest() {
    Assert.assertEquals("Register convention violated $s0", this.reg_s0, get(s0));
    Assert.assertEquals("Register convention violated $s1", this.reg_s1, get(s1));
    Assert.assertEquals("Register convention violated $s2", this.reg_s2, get(s2));
    Assert.assertEquals("Register convention violated $s3", this.reg_s3, get(s3));
    Assert.assertEquals("Register convention violated $s4", this.reg_s4, get(s4));
    Assert.assertEquals("Register convention violated $s5", this.reg_s5, get(s5));
    Assert.assertEquals("Register convention violated $s6", this.reg_s6, get(s6));
    Assert.assertEquals("Register convention violated $s7", this.reg_s7, get(s7));
    Assert.assertEquals("Register convention violated $sp", this.reg_sp, get(sp));
  }

  @Test
  public void verify_input_1() {
    int rows = 2;
    int cols = 3;
    int[] numbers = new int[] {1,2,3,4,5,6};
    Label filename = asciiData(true, "inputs/input1.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 8; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 8; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_input_2() {
    int rows = 5;
    int cols = 5;
    int[] numbers = new int[] {1,2,3,4,5,4,5,6,7,8,9,3,2,7,1,0,1,3,9,2,5,6,7,1,3};
    Label filename = asciiData(true, "inputs/input2.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 27; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 27; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_input_3() {
    int rows = 3;
    int cols = 6;
    int[] numbers = new int[] {1,2,3,4,5,7,4,5,6,7,8,6,9,3,2,7,1,3};
    Label filename = asciiData(true, "inputs/input3.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 20; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 20; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_input_4() {
    int rows = 7;
    int cols = 2;
    int[] numbers = new int[] {1,2,4,5,9,3,3,7,3,8,2,2,1,9};
    Label filename = asciiData(true, "inputs/input4.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 16; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 16; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_dup1p() {
    int rows = 3;
    int cols = 5;
    int[] numbers = new int[] {1,0,0,1,0,0,1,1,0,0,1,0,0,1,0};
    Label filename = asciiData(true, "inputs/dup1p.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 17; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 17; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_dup1f() {
    int rows = 3;
    int cols = 5;
    int[] numbers = new int[] {1,0,0,1,0,0,1,1,0,0,1,1,0,1,1};
    Label filename = asciiData(true, "inputs/dup1f.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 17; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 17; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_dup2p() {
    int rows = 5;
    int cols = 5;
    int[] numbers = new int[] {1,0,0,1,0,0,1,1,0,0,1,1,0,1,1,0,1,1,0,0,1,0,0,1,0};
    Label filename = asciiData(true, "inputs/dup2p.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 27; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 27; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_dup2f() {
    int rows = 5;
    int cols = 5;
    int[] numbers = new int[] {1,0,0,1,0,0,1,1,0,1,1,1,0,1,1,0,1,0,0,0,0,0,0,1,0};
    Label filename = asciiData(true, "inputs/dup2f.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 27; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 27; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_dup3p() {
    int rows = 6;
    int cols = 9;
    int[] numbers = new int[] {1,0,0,1,0,1,0,1,1,1,0,0,1,0,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,1,0,1,1,1,0,1,1,1,1,0,1,1};
    Label filename = asciiData(true, "inputs/dup3p.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 56; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 56; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_dup3f() {
    int rows = 6;
    int cols = 9;
    int[] numbers = new int[] {1,0,0,1,0,1,0,1,1,1,0,0,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0};
    Label filename = asciiData(true, "inputs/dup3f.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 56; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    for(int i = 56; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_input_5() {
    int rows = 9;
    int cols = 9;
    int[] numbers = new int[] {1,2,3,4,5,6,7,8,9,2,1,4,3,9,8,7,6,1,8,1,4,3,9,2,7,6,1,7,4,8,1,9,3,7,6,0,0,1,4,3,9,8,7,6,5,2,1,3,4,9,7,8,6,1,1,3,9,9,3,8,7,6,1,6,4,8,3,9,1,2,3,4,1,3,9,9,3,8,7,6,2};
    Label filename = asciiData(true, "inputs/input5.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8383);
    run("initialize", filename, buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(rows, getWord(buffer, 0));
    Assert.assertEquals(cols, getWord(buffer, 4));
    for(int i = 2; i < 83; i++) {
      Assert.assertEquals(numbers[i-2], getWord(buffer, i*4));
    }
    //verify that buffer[83] did not change
    Assert.assertEquals(8383, getWord(buffer, 83*4));
  }

  @Test
  public void verify_bad_input_1() {
    Label filename = asciiData(true, "inputs/input6.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, getWord(buffer, 0));
    Assert.assertEquals(0, getWord(buffer, 4));
    for(int i = 0; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_bad_input_2() {
    Label filename = asciiData(true, "inputs/input7.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, getWord(buffer, 0));
    Assert.assertEquals(0, getWord(buffer, 4));
    for(int i = 0; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_bad_input_3() {
    Label filename = asciiData(true, "inputs/badup.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, getWord(buffer, 0));
    Assert.assertEquals(0, getWord(buffer, 4));
    for(int i = 0; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_bad_input_4() {
    Label filename = asciiData(true, "inputs/input8.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, getWord(buffer, 0));
    Assert.assertEquals(0, getWord(buffer, 4));
    for(int i = 0; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_bad_input_5() {
    Label filename = asciiData(true, "inputs/input9.txt");
    Label buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, buffer);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, getWord(buffer, 0));
    Assert.assertEquals(0, getWord(buffer, 4));
    for(int i = 0; i < 83; i++) {
      Assert.assertEquals(0, getWord(buffer, i*4));
    }
  }

  @Test
  public void verify_out_1() {
    String[] expected = new String[] {"3", "2", "12", "34", "56"};
    Label filename = asciiData(true, "out.txt");
    Label buffer = wordData(3,2,1,2,3,4,5,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("write_file", filename, buffer);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
       Assert.assertEquals(expected[i], line);
       i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_out_2() {
    String[] expected = new String[] {"3", "3", "690", "341", "762"};
    Label filename = asciiData(true, "out.txt");
    Label buffer = wordData(3,3,6,9,0,3,4,1,7,6,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("write_file", filename, buffer);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
       Assert.assertEquals(expected[i], line);
       i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_out_3() {
    String[] expected = new String[] {"5", "8", "36923692", "21317191", "76298843", "19891992", "25211578"};
    Label filename = asciiData(true, "out.txt");
    Label buffer = wordData(5,8,3,6,9,2,3,6,9,2,2,1,3,1,7,1,9,1,7,6,2,9,8,8,4,3,1,9,8,9,1,9,9,2,2,5,2,1,1,5,7,8);
    run("write_file", filename, buffer);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
       Assert.assertEquals(expected[i], line);
       i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_90_1() {
    String[] expected = new String[] {"3", "2", "41", "52", "63"};
    Label filename = asciiData(true, "inputs/input1.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_90", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_90_2() {
    String[] expected = new String[] {"5", "5", "50941", "61352", "73263", "19774", "32185"};
    Label filename = asciiData(true, "inputs/input2.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_90", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_90_3() {
    String[] expected = new String[] {"6", "3", "941", "352", "263", "774", "185", "367"};
    Label filename = asciiData(true, "inputs/input3.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_90", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_90_4() {
    String[] expected = new String[] {"2", "7", "1233941", "9287352"};
    Label filename = asciiData(true, "inputs/input4.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_90", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_90_5() {
    String[] expected = new String[] {"9", "9", "161207821", "343114112", "989348443", "939431334", "393999995", "818783286", "727877777", "636666668", "241150119"};
    Label filename = asciiData(true, "inputs/input5.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_90", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_180_1() {
    String[] expected = new String[] {"2", "3", "654", "321"};
    Label filename = asciiData(true, "inputs/input1.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_180", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_180_2() {
    String[] expected = new String[] {"5", "5", "31765", "29310", "17239", "87654", "54321"};
    Label filename = asciiData(true, "inputs/input2.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_180", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_180_3() {
    String[] expected = new String[] {"3", "6", "317239", "687654", "754321"};
    Label filename = asciiData(true, "inputs/input3.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_180", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_180_4() {
    String[] expected = new String[] {"7", "2", "91", "22", "83", "73", "39", "54", "21"};
    Label filename = asciiData(true, "inputs/input4.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_180", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_180_5() {
    String[] expected = new String[] {"9", "9", "267839931", "432193846", "167839931", "168794312", "567893410", "067391847", "167293418", "167893412", "987654321"};
    Label filename = asciiData(true, "inputs/input5.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_180", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_270_1() {
    String[] expected = new String[] {"3", "2", "36", "25", "14"};
    Label filename = asciiData(true, "inputs/input1.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_270", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_270_2() {
    String[] expected = new String[] {"5", "5", "58123", "47791", "36237", "25316", "14905"};
    Label filename = asciiData(true, "inputs/input2.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_270", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_270_3() {
    String[] expected = new String[] {"6", "3", "763", "581", "477", "362", "253", "149"};
    Label filename = asciiData(true, "inputs/input3.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_270", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_270_4() {
    String[] expected = new String[] {"2", "7", "2537829", "1493321"};
    Label filename = asciiData(true, "inputs/input4.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_270", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_rotate_clkws_270_5() {
    String[] expected = new String[] {"9", "9", "911051142", "866666636", "777778727", "682387818", "599999393", "433134939", "344843989", "211411343", "128702161"};
    Label filename = asciiData(true, "inputs/input5.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("rotate_clkws_270", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_mirror_1() {
    String[] expected = new String[] {"2", "3", "321", "654"};
    Label filename = asciiData(true, "inputs/input1.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("mirror", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_mirror_2() {
    String[] expected = new String[] {"5", "5", "54321", "87654", "17239", "29310", "31765"};
    Label filename = asciiData(true, "inputs/input2.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("mirror", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_mirror_3() {
    String[] expected = new String[] {"3", "6", "754321", "687654", "317239"};
    Label filename = asciiData(true, "inputs/input3.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("mirror", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_mirror_4() {
    String[] expected = new String[] {"7", "2", "21", "54", "39", "73", "83", "22", "91"};
    Label filename = asciiData(true, "inputs/input4.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("mirror", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_mirror_5() {
    String[] expected = new String[] {"9", "9", "987654321", "167893412", "167293418", "067391847", "567893410", "168794312", "167839931", "432193846", "267839931"};
    Label filename = asciiData(true, "inputs/input5.txt");
    Label outFile = asciiData(true, "out.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("mirror", inp_buffer, outFile);
    try (BufferedReader br = new BufferedReader(new FileReader("out.txt")))
    {
      String line;
      int i = 0;
      while ((line = br.readLine()) != null) {
        Assert.assertEquals(expected[i], line);
        i++;
     }
    }
    catch(IOException e) {
      Assert.assertTrue("File IO Error", false);
    }
    catch(Exception e) {
      Assert.assertTrue("Test Errored out ... ", false);
    }
    finally {
      new File("out.txt").delete();
    }
  }

  @Test
  public void verify_dup_1() {
    Label filename = asciiData(true, "inputs/dup1p.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("duplicate", inp_buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(3, get(v1));
  }

  @Test
  public void verify_no_dup_1() {
    Label filename = asciiData(true, "inputs/dup1f.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("duplicate", inp_buffer);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, get(v1));
  }

  @Test
  public void verify_dup_2() {
    Label filename = asciiData(true, "inputs/dup2p.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("duplicate", inp_buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(4, get(v1));
  }

  @Test
  public void verify_no_dup_2() {
    Label filename = asciiData(true, "inputs/dup2f.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("duplicate", inp_buffer);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, get(v1));
  }

  @Test
  public void verify_dup_3() {
    Label filename = asciiData(true, "inputs/dup3p.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("duplicate", inp_buffer);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(5, get(v1));
  }

  @Test
  public void verify_no_dup_3() {
    Label filename = asciiData(true, "inputs/dup3f.txt");
    Label init_buffer = wordData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("initialize", filename, init_buffer);
    Label inp_buffer = wordData(getWords(init_buffer.address(), 83));
    run("duplicate", inp_buffer);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, get(v1));
  }
}
