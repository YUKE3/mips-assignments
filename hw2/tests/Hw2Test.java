import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;

public class Hw2Test {
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
  public void verify_to_upper_1() {
    Label msg = asciiData(true, "This is a Test message");
    run("to_upper", msg);
    Assert.assertEquals("THIS IS A TEST MESSAGE", getString(msg, 0));
  }

  @Test
  public void verify_to_upper_2() {
    Label msg = asciiData(true, "This is a Test meSSage!");
    run("to_upper", msg);
    Assert.assertEquals("THIS IS A TEST MESSAGE!", getString(msg, 0));
  }

  @Test
  public void verify_to_upper_3() {
    Label msg = asciiData(true, "THIS IS A TEST MESSAGE");
    run("to_upper", msg);
    Assert.assertEquals("THIS IS A TEST MESSAGE", getString(msg, 0));
  }

  @Test
  public void verify_to_upper_4() {
    Label msg = asciiData(true, "");
    run("to_upper", msg);
    Assert.assertEquals("", getString(msg, 0));
  }

  @Test
  public void verify_remove_1() {
    Label alphabet = asciiData(true, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    run("remove", alphabet, 2);
    Assert.assertEquals("ABDEFGHIJKLMNOPQRSTUVWXYZ\u0000", getString(alphabet, 0, 26));
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_remove_2() {
    Label alphabet = asciiData(true, "ABDEFGHIJKLMNOPQRSTUVWXYZ\u0000");
    run("remove", alphabet, 8);
    Assert.assertEquals("ABDEFGHIKLMNOPQRSTUVWXYZ\u0000\u0000", getString(alphabet, 0, 26));
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_remove_3() {
    Label alphabet = asciiData(true, "ABDEFGHIKLMNOPQRSTUVWXYZ\u0000\u0000");
    run("remove", alphabet, 0);
    Assert.assertEquals("BDEFGHIKLMNOPQRSTUVWXYZ\u0000\u0000\u0000", getString(alphabet, 0, 26));
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_remove_4() {
    Label alphabet = asciiData(true, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    run("remove", alphabet, 25);
    Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXY\u0000", getString(alphabet, 0, 26));
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_remove_5() {
    Label alphabet = asciiData(true, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    run("remove", alphabet, 27);
    Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", getString(alphabet, 0, 26));
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_remove_6() {
    Label alphabet = asciiData(true, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    run("remove", alphabet, -2);
    Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", getString(alphabet, 0, 26));
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_random_int() {
    for(int i = 0; i < 10; i++) {
      run("getRandomInt", 12);
      Assert.assertTrue(get(v0) >= 0 && get(v0) < 12);
      Assert.assertEquals(1, get(v1));
    }
  }

  @Test
  public void verify_random_int_1() {
    run("getRandomInt", 1);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals(1, get(v1));
  }

  @Test
  public void verify_random_int_2() {
    run("getRandomInt", 0);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, get(v1));
  }

  @Test
  public void verify_random_int_3() {
    run("getRandomInt", -1);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(0, get(v1));
  }

  @Test
  public void verify_cpy_elems_1() {
    Label src = asciiData(true, "ABCDEFGH");
    Label dest = emptyBytes(1);
    run("cpyElems", src, 2, dest);
    Assert.assertEquals("C", getString(dest, 0, 1));
  }
  @Test
  public void verify_cpy_elems_2() {
    Label src = asciiData(true, "ABCDEFGH");
    Label dest = emptyBytes(2);
    run("cpyElems", src, 2, dest);
    run("cpyElems", src, 5, get(v0));
    Assert.assertEquals("F", getString(dest.address()+1, 1));
  }

  @Test
  public void verify_cpy_elems_3() {
    Label src = asciiData(true, "ABCDEFGH");
    Label dest = emptyBytes(3);
    run("cpyElems", src, 2, dest);
    run("cpyElems", src, 3, get(v0));
    run("cpyElems", src, 0, get(v0));
    Assert.assertEquals("A", getString(dest.address()+2, 1));
  }

  @Test
  public void verify_gen_key_size() {
    Label alphabet = asciiData(true, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    Label cipher_key = emptyBytes(26);
    run("genKey", alphabet, cipher_key);
    Assert.assertEquals(26, getString(cipher_key.address()).length());
  }

  @Test
  public void verify_gen_key_content() {
    Label alphabet = asciiData(true, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    Label cipher_key = emptyBytes(26);
    run("genKey", alphabet, cipher_key);
    char[] key_chars = getString(cipher_key.address()).toCharArray();
    for (char ch: key_chars) {
      Assert.assertNotEquals(-1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ ".indexOf(ch));
    }
  }

  @Test
  public void verify_contains_true_1() {
    Label text = asciiData(true, "Test Data 1");
    run("contains", text, 49);
    Assert.assertEquals(10, get(v0));
  }

  @Test
  public void verify_contains_true_2() {
    Label text = asciiData(true, "Test Data 1");
    run("contains", text, 97);
    Assert.assertEquals(6, get(v0));
  }

  @Test
  public void verify_contains_false_1() {
    Label text = asciiData(true, "Test Data 1");
    run("contains", text, 70);
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_contains_false_2() {
    Label text = asciiData(true, "Test Data 1");
    run("contains", text, 69);
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_contains_false_empty() {
    Label text = asciiData(true, "");
    run("contains", text, 69);
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_encrypt_1() {
    String msg = "Encryption is the art of hiding secret information";
    Label plaintext = asciiData(true, msg);
    Label cipherkey = asciiData(true, "WTOSIGARQBXFHUDKNPJVCEZLYM");
    Label ciphertext = asciiData(false, "");
    run("encrypt", plaintext, cipherkey, ciphertext);
    Assert.assertEquals(1, get(v0));
    Label plaintext1 = asciiData(true, getString(ciphertext.address()));
    Label ciphertext1 = asciiData(false, "");
    run("encrypt", plaintext1, cipherkey, ciphertext1);
    Assert.assertEquals(msg.toUpperCase(), getString(ciphertext1.address()));
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_encrypt_2() {
    String msg = "The Vatsyayana Cipher is one of the earliest forms of substitution cipher algorithms";
    Label plaintext = asciiData(true, msg);
    Label cipherkey = asciiData(true, "XTOSIZARQBWFHUDKNPJVCEGLMY");
    Label ciphertext = asciiData(false, "");
    run("encrypt", plaintext, cipherkey, ciphertext);
    Assert.assertEquals(1, get(v0));
    Label plaintext1 = asciiData(true, getString(ciphertext.address()));
    Label ciphertext1 = asciiData(false, "");
    run("encrypt", plaintext1, cipherkey, ciphertext1);
    Assert.assertEquals(msg.toUpperCase(), getString(ciphertext1.address()));
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_encrypt_3() {
    String msg = "Ice cream sandwich";
    Label plaintext = asciiData(true, msg);
    Label alphabet = asciiData(true, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    Label cipher_key = emptyBytes(26);
    run("genKey", alphabet, cipher_key);
    String key = getString(cipher_key.address(), 26);
    Label keylabel = asciiData(true, key);
    Label ciphertext = asciiData(false, "");
    run("encrypt", plaintext, keylabel, ciphertext);
    Assert.assertEquals(1, get(v0));
    String cipher1 = getString(ciphertext.address());
    Label plaintext1 = asciiData(true, cipher1);
    Label keylabel1 = asciiData(true, key);
    Label ciphertext1 = asciiData(false, "");
    run("encrypt", plaintext1, keylabel1, ciphertext1);
    Assert.assertEquals(msg.toUpperCase(), getString(ciphertext1.address()));
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_encrypt_fail_1() {
    String msg = "Encryption is the art of hiding secret information!";
    Label plaintext = asciiData(true, msg);
    Label cipherkey = asciiData(true, "WTOSIGARQBXFHUDKNPJVCEZLYM");
    Label ciphertext = asciiData(false, "");
    run("encrypt", plaintext, cipherkey, ciphertext);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("", getString(ciphertext.address()));
  }

  @Test
  public void verify_encrypt_fail_2() {
    String msg = "Encryption is the art-of hiding secret information";
    Label plaintext = asciiData(true, msg);
    Label cipherkey = asciiData(true, "WTOSIGARQBXFHUDKNPJVCEZLYM");
    Label ciphertext = asciiData(false, "");
    run("encrypt", plaintext, cipherkey, ciphertext);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("", getString(ciphertext.address()));
  }

  @Test
  public void verify_encrypt_fail_3() {
    String msg = "CSE220 is a course on system fundamentals";
    Label plaintext = asciiData(true, msg);
    Label cipherkey = asciiData(true, "WTOSIGARQBXFHUDKNPJVCEZLYM");
    Label ciphertext = asciiData(false, "");
    run("encrypt", plaintext, cipherkey, ciphertext);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("", getString(ciphertext.address()));
  }

  @Test
  public void verify_pair_exists_1() {
    Label key = asciiData(true, "AYGZ");
    run("pair_exists", 65, 89, key);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_pair_exists_2() {
    Label key = asciiData(true, "AYGZ");
    run("pair_exists", 89, 65, key);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_pair_exists_3() {
    Label key = asciiData(true, "AYGZ");
    run("pair_exists", 71, 90, key);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_pair_exists_4() {
    Label key = asciiData(true, "AYGZ");
    run("pair_exists", 90, 71, key);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_pair_exists_5() {
    Label key = asciiData(true, "AYGZH");
    run("pair_exists", 90, 71, key);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_pair_exists_6() {
    Label key = asciiData(true, "AYGZHL");
    run("pair_exists", 71, 72, key);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_pair_exists_7() {
    Label key = asciiData(true, "AY1ZHL");
    run("pair_exists", 49, 90, key);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_pair_exists_8() {
    Label key = asciiData(false, "");
    run("pair_exists", 88, 90, key);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_pair_exists_9() {
    Label key = asciiData(false, "AYGZH");
    run("pair_exists", 90, 71, key);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_decipher_key_chosen_plainttext_1() {
    Label plaintxt = asciiData(true, "LATTE");
    Label ciphertxt = asciiData(true, "TXLLQ");
    Label key = asciiData(true, "");
    run("decipher_key_with_chosen_plaintext", plaintxt, ciphertxt, key);
    Assert.assertEquals("LTAXEQ", getString(key.address()));
  }

  @Test
  public void verify_decipher_key_chosen_plainttext_2() {
    Label plaintxt = asciiData(true, "I JUST BROKE A CRYPTO");
    Label ciphertxt = asciiData(true, "Q XAWD KORBS U HOLMDR");
    Label key = asciiData(true, "");
    run("decipher_key_with_chosen_plaintext", plaintxt, ciphertxt, key);
    Assert.assertEquals("IQJXUASWTDBKROESCHYLPM", getString(key.address()));
  }

  @Test
  public void verify_decipher_key_chosen_plainttext_3() {
    Label plaintxt = asciiData(true, "ENCRYPTION NOT DONE RIGHT IS UNSAFE");
    Label ciphertxt = asciiData(true, "ABTQZDCLWB BWC PWBA QLKMC LF JBFESA");
    Label key = asciiData(true, "");
    run("decipher_key_with_chosen_plaintext", plaintxt, ciphertxt, key);
    Assert.assertEquals("EANBCTRQYZPDILOWGKHMSFUJ", getString(key.address()));
  }
}
