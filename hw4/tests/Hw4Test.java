import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;
import static edu.gvsu.mipsunit.munit.MARSSimulator.*;
import java.io.*;
import java.util.Arrays;

public class Hw4Test {

  @Test
  public void verify_create_person_success_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,0,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("create_person", ntwrk_info);
    Assert.assertEquals(nodes.address(), get(v0));
    Assert.assertEquals(1, getWord(ntwrk_info.address()+16));
    Assert.assertEquals("Did you not initialize person node with 0s?", 60, Arrays.stream(getWords(nodes.address(),60)).filter(val-> val == 0).count());
  }

  @Test
  public void verify_create_person_success_2() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,3,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("create_person", ntwrk_info);
    Assert.assertEquals(nodes.address()+36, get(v0));
    Assert.assertEquals(4, getWord(ntwrk_info.address()+16));
    Assert.assertEquals("Did you not initialize person node with 0s?", 60, Arrays.stream(getWords(nodes.address(),60)).filter(val-> val == 0).count());
  }

  @Test
  public void verify_create_person_success_3() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,4,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("create_person", ntwrk_info);
    Assert.assertEquals(nodes.address()+48, get(v0));
    Assert.assertEquals(5, getWord(ntwrk_info.address()+16));
  }

  @Test
  public void verify_create_person_fail() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("create_person", ntwrk_info);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(5, getWord(ntwrk_info.address()+16));
  }

  @Test
  public void verify_person_exists_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,1,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("is_person_exists", ntwrk_info, nodes);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_person_exists_2() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,2,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("is_person_exists", ntwrk_info, nodes);
    Assert.assertEquals(1, get(v0));
    run("is_person_exists", ntwrk_info, nodes.address()+12);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_person_exists_3() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,4,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("is_person_exists", ntwrk_info, nodes);
    Assert.assertEquals(1, get(v0));
    run("is_person_exists", ntwrk_info, nodes.address()+36);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_person_not_exists_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,4,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("is_person_exists", ntwrk_info, nodes);
    Assert.assertEquals(1, get(v0));
    set(v0, -1);
    run("is_person_exists", ntwrk_info, nodes.address()+10);
    Assert.assertEquals("Invalid person address passed but not detected!", 0, get(v0));
  }

  @Test
  public void verify_person_not_exists_2() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    set(v0,-101);
    run("is_person_exists", ntwrk_info, 404);
    Assert.assertEquals("Invalid person address passed but not detected!", 0, get(v0));
  }

  @Test
  public void verify_person_not_exists_3() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,4,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    run("is_person_exists", ntwrk_info, nodes);
    Assert.assertEquals(1, get(v0));
    set(v0,-1);
    run("is_person_exists", ntwrk_info, nodes.address()+29);
    Assert.assertEquals("Invalid person address passed but not detected!", 0, get(v0));
  }

  @Test
  public void verify_person_name_exists_1() {
    Label some_name = asciiData(true, "Kebla");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,2,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("is_person_name_exists", ntwrk_info, some_name);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(nodes.address(), get(v1));
  }

  @Test
  public void verify_person_name_exists_2() {
    Label some_name = asciiData(true, "Boka");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,2,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("is_person_name_exists", ntwrk_info, some_name);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(nodes.address()+12, get(v1));
  }

  @Test
  public void verify_person_name_exists_3() {
    Label some_name = asciiData(true, "Wasundhara");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("is_person_name_exists", ntwrk_info, some_name);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(nodes.address()+48, get(v1));
  }

  @Test
  public void verify_person_name_notexists_1() {
    Label some_name = asciiData(true, "Wasundhara");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000WasundharHu\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, -292);
    run("is_person_name_exists", ntwrk_info, some_name);
    Assert.assertEquals("What! Did you just detected a non-existent person name?", 0, get(v0));
  }

  @Test
  public void verify_person_name_notexists_2() {
    Label some_name = asciiData(true, "Kebla");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla Kumar\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 21);
    run("is_person_name_exists", ntwrk_info, some_name);
    Assert.assertEquals("Not good! You found a non-existent person name.", 0, get(v0));
  }

  @Test
  public void verify_person_name_notexists_3() {
    Label some_name = asciiData(true, "jhamela");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla Kumar\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 29);
    run("is_person_name_exists", ntwrk_info, some_name);
    Assert.assertEquals("Trouble! You are detecting non-existent names!", 0, get(v0));
  }

  @Test
  public void verify_add_person_property_success_1() {
    Label prop_val = asciiData(true, "Haanda");
    Label prop_name = asciiData(true, "NAME");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,2,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
  }

  @Test
  public void verify_add_person_property_success_2() {
    Label prop_val = asciiData(true, "02022000");
    Label prop_name = asciiData(true, "DOB");
    Label prop_name_1 = asciiData(true, "NAME");
    Label prop_val_1 = asciiData(true, "alibaba");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,2,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    run("add_person_property", ntwrk_info, nodes.address()+12, prop_name_1, prop_val_1);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals("alibaba", getString(nodes.address()+12));
  }

  @Test
  public void verify_add_person_property_success_3() {
    Label prop_val = asciiData(true, "02022000");
    Label prop_name = asciiData(true, "DOB");
    Label prop_name_1 = asciiData(true, "NAME");
    Label prop_val_1 = asciiData(true, "Bhola Zhang");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
    run("add_person_property", ntwrk_info, nodes.address()+48, prop_name_1, prop_val_1);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals("Bhola Zhang", getString(nodes.address()+48));
  }

  @Test
  public void verify_add_person_property_person_notexists_1() {
    Label prop_val = asciiData(true, "02022000");
    Label prop_name = asciiData(true, "DOB");
    Label prop_name_1 = asciiData(true, "NAME");
    Label prop_val_1 = asciiData(true, "Bhola Zhang");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
    run("add_person_property", ntwrk_info, nodes.address()+60, prop_name_1, prop_val_1);
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_add_person_property_person_notexists_2() {
    Label prop_val = asciiData(true, "02022000");
    Label prop_name = asciiData(true, "DOB");
    Label prop_name_1 = asciiData(true, "NAME");
    Label prop_val_1 = asciiData(true, "Bhola Zhang");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
    run("add_person_property", ntwrk_info, nodes.address()+1982, prop_name_1, prop_val_1);
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_add_person_property_person_notexists_3() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "kuku");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, frnd_prop, prop_name, prop_val);
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_add_person_property_badname_1() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Oshleen Harris");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(-2, get(v0));
  }

  @Test
  public void verify_add_person_property_dupname_1() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Haanda");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Haanda\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(-3, get(v0));
    Assert.assertEquals("Haanda", getString(nodes.address()));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_add_person_property_dupname_2() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "lat");
    Label prop_name_1 = asciiData(true, "DOB");
    Label prop_val_1 = asciiData(true, "0901");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Jali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name_1, prop_val_1);
    run("add_person_property", ntwrk_info, nodes.address()+12, prop_name, prop_val);
    Assert.assertEquals(-3, get(v0));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Jali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_add_person_property_dupname_3() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Kali");
    Label prop_name_1 = asciiData(true, "DOB");
    Label prop_val_1 = asciiData(true, "0901");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name_1, prop_val_1);
    run("add_person_property", ntwrk_info, nodes.address()+36, prop_name, prop_val);
    Assert.assertEquals(-3, get(v0));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Kali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_add_person_property_dupname_4() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Kali");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name, prop_val);
    Assert.assertEquals(-3, get(v0));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Kali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_add_person_property_dupname_5() {
    Label prop_name = asciiData(true, "NAME");
    Label prop_val = asciiData(true, "Hulo");
    Label prop_name_1 = asciiData(true, "DOB");
    Label prop_val_1 = asciiData(true, "0901");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("add_person_property", ntwrk_info, nodes, prop_name_1, prop_val_1);
    run("add_person_property", ntwrk_info, nodes.address()+48, prop_name, prop_val);
    Assert.assertEquals(-3, get(v0));
    Assert.assertEquals("lat", getString(nodes.address()+12));
    Assert.assertEquals("Kali", getString(nodes.address()+24));
    Assert.assertEquals("Hulo", getString(nodes.address()+36));
  }

  @Test
  public void verify_get_person_success_1() {
    Label name = asciiData(true, "Kimchee");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("Kimchee", getString(get(v0)));
  }

  @Test
  public void verify_get_person_success_2() {
    Label name = asciiData(true, "lat");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000lat\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("lat", getString(get(v0)));
  }

  @Test
  public void verify_get_person_success_3() {
    Label name = asciiData(true, "Kali");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals("Kali", getString(get(v0)));
  }

  @Test
  public void verify_get_person_fail_1() {
    Label name = asciiData(true, "lat");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, -12);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_get_person_fail_2() {
    Label name = asciiData(true, "Kim");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, -11);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_get_person_fail_3() {
    Label name = asciiData(true, "Keshto");
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Yolanda\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kali\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Hulo\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Kimchee\u0000\u0000\u0000\u0000\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    Label edges = emptyWords(30);
    set(v0, 12);
    run("get_person", ntwrk_info, name);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_relation_exists_true_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,1);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,-1,303,404,505,1202,1212,228,8272,1298,9089,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_relation_exists", ntwrk_info, nodes.address(), nodes.address()+12);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_relation_exists_true_2() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,2);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,-1,nodes.address()+48,nodes.address()+36,505,1202,1212,228,11,99,9089,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_relation_exists", ntwrk_info, nodes.address()+36, nodes.address()+48);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_relation_exists_true_3() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,5);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,-1,nodes.address()+48,nodes.address()+36,2314,nodes.address()+12,nodes.address()+48,1212,nodes.address(),nodes.address()+48,99,9089,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_relation_exists", ntwrk_info, nodes.address()+48, nodes.address()+12);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_relation_exists_true_4() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,5);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,-1,nodes.address()+48,nodes.address()+36,2314,nodes.address()+12,nodes.address()+48,1212,nodes.address(),nodes.address()+48,99,9089,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_relation_exists", ntwrk_info, nodes.address(), nodes.address()+48);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_relation_exists_false_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,-1,nodes.address()+48,nodes.address()+36,2314,nodes.address()+12,nodes.address()+48,1212,nodes.address(),nodes.address()+48,99,9089,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0, 99);
    run("is_relation_exists", ntwrk_info, nodes.address()+36, nodes.address()+48);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_relation_exists_false_2() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,5);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,-1,nodes.address()+48,nodes.address()+36,2314,nodes.address()+12,nodes.address()+48,1212,nodes.address(),nodes.address()+48,99,9089,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0, -99);
    run("is_relation_exists", ntwrk_info, nodes.address()+24, nodes.address()+48);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_relation_exists_false_3() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,5);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,-1,nodes.address()+48,nodes.address()+36,2314,nodes.address()+12,nodes.address()+48,1212,nodes.address(),nodes.address()+48,99,9089,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0,21);
    run("is_relation_exists", ntwrk_info, nodes.address()+72, nodes.address()+24);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_add_relation_success_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,0);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    run("add_relation", ntwrk_info, nodes.address()+48, nodes.address()+12);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(1, getWord(ntwrk_info.address()+20));
    Assert.assertEquals(nodes.address()+48, getWord(edges.address()));
    Assert.assertEquals(nodes.address()+12, getWord(edges.address()+4));
    Assert.assertTrue("Added friend relation but was not expected!", 1 != getWord(edges.address()+8));
  }

  @Test
  public void verify_add_relation_success_2() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,2);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    run("add_relation", ntwrk_info, nodes.address(), nodes.address()+24);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(3, getWord(ntwrk_info.address()+20));
    Assert.assertEquals(nodes.address(), getWord(edges.address()+24));
    Assert.assertEquals(nodes.address()+24, getWord(edges.address()+28));
    Assert.assertTrue("Added friend relation but was not expected!", 1 != getWord(edges.address()+32));
  }

  @Test
  public void verify_add_relation_success_3() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,9);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    run("add_relation", ntwrk_info, nodes.address()+36, nodes.address()+24);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals(10, getWord(ntwrk_info.address()+20));
    Assert.assertEquals(nodes.address()+36, getWord(edges.address()+108));
    Assert.assertEquals(nodes.address()+24, getWord(edges.address()+112));
    Assert.assertTrue("Added friend relation but was not expected!", 1 != getWord(edges.address()+120));
  }

  @Test
  public void verify_add_relation_fail_noperson_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,9);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    int[] preEdgeVals = getWords(edges.address(), 30);
    set(v0, 999);
    run("add_relation", ntwrk_info, nodes.address()+60, nodes.address()+24);
    int[] postEdgeVals = getWords(edges.address(), 30);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals(9, getWord(ntwrk_info.address()+20));
    Assert.assertArrayEquals("Oh no! You changed edges when you shouldn't have!", preEdgeVals, postEdgeVals);
  }

  @Test
  public void verify_add_relation_fail_noperson_2() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,9);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    int[] preEdgeVals = getWords(edges.address(), 30);
    set(v0, -19);
    run("add_relation", ntwrk_info, nodes.address()+12, nodes.address()+60);
    int[] postEdgeVals = getWords(edges.address(), 30);
    Assert.assertEquals(0, get(v0));
    Assert.assertEquals(9, getWord(ntwrk_info.address()+20));
    Assert.assertArrayEquals("Oh no! You changed edges when you shouldn't have!", preEdgeVals, postEdgeVals);
  }

  @Test
  public void verify_add_relation_fail_limit_1() {
    // make network struct
    Label ntwrk_info = wordData(5,10,12,12,5,10);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    Label edges = emptyWords(30);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    int[] preEdgeVals = getWords(edges.address(), 30);
    run("add_relation", ntwrk_info, nodes.address()+12, nodes.address()+48);
    int[] postEdgeVals = getWords(edges.address(), 30);
    Assert.assertEquals(-1, get(v0));
    Assert.assertEquals(10, getWord(ntwrk_info.address()+20));
    Assert.assertArrayEquals("Oh no! You changed edges when you shouldn't have!", preEdgeVals, postEdgeVals);
  }

  @Test
  public void verify_add_relation_fail_relexists_1() {
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,12,nodes.address()+48,nodes.address()+36,29,nodes.address()+12,nodes.address()+48,33,404,505,999,0,0,0,0,0,0,0,0,0,0,0,0,0,0,777,0,0,0);
    run("add_relation", ntwrk_info, nodes.address()+12,nodes.address()+48);
    Assert.assertEquals("Stop! You are changing existing relationships!",-2, get(v0));
    Assert.assertEquals(3, getWord(ntwrk_info.address()+20));
  }

  @Test
  public void verify_add_relation_fail_relexists_2() {
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,7,nodes.address()+12,nodes.address()+48,13,0,0,0,0,0,0,0,0,0,288,0,0,919,0,0,0,0,0,0,0,0);
    run("add_relation", ntwrk_info, nodes.address()+48,nodes.address()+12);
    Assert.assertEquals("Stop! You are changing existing relationships!",-2, get(v0));
    Assert.assertEquals(3, getWord(ntwrk_info.address()+20));
  }

  @Test
  public void verify_add_relation_fail_samerel_1() {
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,918,0,0,192,0,0,0,0,191,0,0,336,0,0,0,0,0,0,0,0,0,0);
    run("add_relation", ntwrk_info, nodes.address()+48,nodes.address()+48);
    Assert.assertEquals("Strange! Why relate people to themselves?",-3, get(v0));
    Assert.assertEquals("Relate people to themselves should not change the network!", 3, getWord(ntwrk_info.address()+20));
  }

  @Test
  public void verify_add_relation_fail_samerel_2() {
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("add_relation", ntwrk_info, nodes.address()+12,nodes.address()+12);
    Assert.assertEquals("Strange! why relate people to themselves?",-3, get(v0));
    Assert.assertEquals("Please! relating people to themselves should not change the network!", 3, getWord(ntwrk_info.address()+20));
  }

  @Test
  public void verify_add_relation_property_frnd_success_1() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("add_relation_property", ntwrk_info, nodes.address()+12,nodes.address(), rel_prop);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals("Gotcha! property value may be incorrect.", 1, getWord(edges.address()+8));
  }

  @Test
  public void verify_add_relation_property_frnd_success_2() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("add_relation_property", ntwrk_info, nodes.address()+48,nodes.address()+36, rel_prop);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals("Gotcha! property value may be incorrect.", 1, getWord(edges.address()+20));
  }

  @Test
  public void verify_add_relation_property_frnd_success_3() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("add_relation_property", ntwrk_info, nodes.address()+48,nodes.address()+12, rel_prop);
    Assert.assertEquals(1, get(v0));
    Assert.assertEquals("Gotcha! property value may be incorrect.", 1, getWord(edges.address()+32));
  }

  @Test
  public void verify_add_relation_property_frnd_no_rel_fail_1() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0, -128);
    run("add_relation_property", ntwrk_info, nodes.address()+48,nodes.address()+24, rel_prop);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_add_relation_property_frnd_no_rel_fail_2() {
    Label rel_prop = asciiData(true, "FRIEND");
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    set(v0, -404);
    run("add_relation_property", ntwrk_info, nodes.address()+48,nodes.address(), rel_prop);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_add_relation_property_not_frnd_fail() {
    Label rel_prop = asciiData(true, "SIBLING");
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    Label nodes = emptyBytes(60);
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address()+48,nodes.address()+36,0,nodes.address()+12,nodes.address()+48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("add_relation_property", ntwrk_info, nodes.address()+48,nodes.address()+36, rel_prop);
    int[] edgeVals = getWords(edges.address(),30);
    Assert.assertEquals(-1, get(v0));
    for(int i = 2; i < 30; i=i+3) {
      Assert.assertEquals("Gotcha! Are you trying to modify the network?", 0, edgeVals[i]);
    }
  }

  @Test
  public void verify_is_friend_of_friend_true_1() {
    Label name1 = asciiData(true, "Wasundhara");
    Label name2 = asciiData(true, "Boka");
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address()+48,nodes.address(),1,nodes.address()+12,nodes.address(),1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_true_2() {
    Label name1 = asciiData(true, "Boka");
    Label name2 = asciiData(true, "Wasundhara");
    Label ntwrk_info = wordData(5,10,12,12,5,3);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address()+48,nodes.address(),1,nodes.address()+12,nodes.address(),1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_true_3() {
    Label name1 = asciiData(true, "Boka");
    Label name2 = asciiData(true, "JHAMELA");
    Label ntwrk_info = wordData(5,10,12,12,5,7);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000JHAMELA\u0000\u0000\u0000\u0000\u0000Kelane\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,1,nodes.address(),nodes.address()+24,1,nodes.address(),nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,nodes.address()+12,nodes.address()+48,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+36,1,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_true_4() {
    Label name1 = asciiData(true, "Kebla");
    Label name2 = asciiData(true, "Boka");
    Label ntwrk_info = wordData(5,10,12,12,5,7);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000JHAMELA\u0000\u0000\u0000\u0000\u0000Kelane\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,0,nodes.address(),nodes.address()+24,1,nodes.address(),nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,nodes.address()+12,nodes.address()+48,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+36,1,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(1, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_false_1() {
    Label name1 = asciiData(true, "Kebla");
    Label name2 = asciiData(true, "Kelane");
    Label ntwrk_info = wordData(5,10,12,12,5,7);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000JHAMELA\u0000\u0000\u0000\u0000\u0000Kelane\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,1,nodes.address(),nodes.address()+24,1,nodes.address(),nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,nodes.address()+12,nodes.address()+48,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+36,1,0,0,0,0,0,0,0,0,0);
    set(v0, -710);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_false_2() {
    Label name1 = asciiData(true, "Kelane");
    Label name2 = asciiData(true, "Boka");
    Label ntwrk_info = wordData(5,10,12,12,5,7);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000JHAMELA\u0000\u0000\u0000\u0000\u0000Kelane\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    set(v0, 004);
    Label edges = wordData(nodes.address(),nodes.address()+12,1,nodes.address(),nodes.address()+24,1,nodes.address(),nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,nodes.address()+12,nodes.address()+48,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+36,1,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_false_3() {
    Label name1 = asciiData(true, "Kebla");
    Label name2 = asciiData(true, "Boka");
    Label ntwrk_info = wordData(5,10,12,12,5,7);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000JHAMELA\u0000\u0000\u0000\u0000\u0000Kelane\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    set(v0, 452);
    Label edges = wordData(nodes.address(),nodes.address()+12,1,nodes.address(),nodes.address()+24,1,nodes.address(),nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,nodes.address()+12,nodes.address()+48,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+36,1,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(0, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_false_4() {
    Label name1 = asciiData(true, "Posto");
    Label name2 = asciiData(true, "Boka");
    Label ntwrk_info = wordData(5,10,12,12,5,7);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000JHAMELA\u0000\u0000\u0000\u0000\u0000Kelane\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,1,nodes.address(),nodes.address()+24,1,nodes.address(),nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,nodes.address()+12,nodes.address()+48,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+36,1,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_false_5() {
    Label name1 = asciiData(true, "Kebla");
    Label name2 = asciiData(true, "Keshto");
    Label ntwrk_info = wordData(5,10,12,12,5,7);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000JHAMELA\u0000\u0000\u0000\u0000\u0000Kelane\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,1,nodes.address(),nodes.address()+24,1,nodes.address(),nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,nodes.address()+12,nodes.address()+48,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+36,1,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(-1, get(v0));
  }

  @Test
  public void verify_is_friend_of_friend_false_6() {
    Label name1 = asciiData(true, "Pashto");
    Label name2 = asciiData(true, "Keshto");
    Label ntwrk_info = wordData(5,10,12,12,5,7);
    Label name_prop = asciiData(true, "NAME");
    Label frnd_prop = asciiData(true, "FRIEND");
    String node_data = "Kebla\u0000\u0000\u0000\u0000\u0000\u0000\u0000Boka\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000JHAMELA\u0000\u0000\u0000\u0000\u0000Kelane\u0000\u0000\u0000\u0000\u0000\u0000Wasundhara\u0000\u0000";
    Label nodes = byteData(node_data.getBytes());
    // dummy method call to obtain nodes address.
    run("create_person", ntwrk_info);
    Label edges = wordData(nodes.address(),nodes.address()+12,1,nodes.address(),nodes.address()+24,1,nodes.address(),nodes.address()+36,1,nodes.address()+24,nodes.address()+48,1,nodes.address()+12,nodes.address()+48,1,nodes.address()+12,nodes.address()+36,1,nodes.address()+24,nodes.address()+36,1,0,0,0,0,0,0,0,0,0);
    run("is_friend_of_friend", ntwrk_info, name1, name2);
    Assert.assertEquals(-1, get(v0));
  }
}
