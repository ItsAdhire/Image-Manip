package model;

import org.junit.Test;

import java.util.HashMap;

import model.Image;
import model.SimpleImageModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents a set of tests for the SimpleImageModel class.
 */
public class SimpleImageModelTest {
  String s1;
  String s2;
  String s3;
  String s4;
  int[] pixels1;
  int[] pixels2;
  int[] pixels3;
  int[] pixels4;
  int width1;
  int width2;
  int width3;
  int width4;
  int height1;
  int height2;
  int height3;
  int height4;
  Image image1;
  Image image2;
  Image image3;
  Image image4;
  HashMap<String, Image> map1;
  HashMap<String, Image> map2;
  HashMap<String, Image> map3;
  SimpleImageModel sim1;
  SimpleImageModel sim2;
  SimpleImageModel sim3;

  // Tests for an IllegalArgumentException thrown by the retrieve method in the
  // SimpleImageModel class
  @Test
  public void testInvalidRetrieve() {
    this.sim1 = new SimpleImageModel();
    try {
      this.sim1.retrieve(null);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given null inputs", e.getMessage());
    }
  }

  // Tests for an IllegalArgumentException thrown by the add method in the
  // SimpleImageModel class
  @Test
  public void testInvalidAdd() {
    this.width1 = 1;
    this.height1 = 1;
    this.pixels1 = new int[]{20};
    this.image1 = new Image(this.pixels1, this.width1, this.height1);
    this.sim1 = new SimpleImageModel();
    try {
      this.sim1.add(null, this.image1);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given null inputs", e.getMessage());
    }

    this.image2 = null;
    this.sim2 = new SimpleImageModel();
    try {
      this.sim2.add("hello", this.image2);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given null inputs", e.getMessage());
    }
  }

  // Tests the add method (and by extension, the retrieve method) in the SimpleImageModel class
  @Test
  public void testValidAddAndRetrieve() {
    this.sim1 = new SimpleImageModel();

    assertEquals(null, this.sim1.retrieve("bug"));

    this.width1 = 1;
    this.height1 = 1;
    this.pixels1 = new int[]{20};
    this.image1 = new Image(this.pixels1, this.width1, this.height1);
    this.sim1.add("bug", this.image1);

    assertEquals(this.image1, this.sim1.retrieve("bug"));

    assertEquals(null, this.sim1.retrieve("flower"));

    this.width2 = 4;
    this.height2 = 4;
    this.pixels2 = new int[]{-10000, -7830, -400, -15, -1, -7830, 0, 2,
      3, 4, 14, 38, 4, 210, 13781, 123456};
    this.image2 = new Image(this.pixels2, this.width2, this.height2);
    this.sim1.add("house", this.image2);

    assertEquals(this.image2, this.sim1.retrieve("house"));

    this.sim1.add("apartment", this.image2);

    assertEquals(this.image2, this.sim1.retrieve("apartment"));

    this.width3 = 2;
    this.height3 = 3;
    this.pixels3 = new int[]{-34500, -15, 5, 27, 176, 210};
    this.image3 = new Image(this.pixels3, this.width3, this.height3);
    this.sim1.add("tennis ball", this.image3);

    assertEquals(this.image3, this.sim1.retrieve("tennis ball"));

    this.width4 = 5;
    this.height4 = 4;
    this.pixels4 = new int[]{-2459060, -32949, -10000, -7830, -400, -15,
      -1, -7830, 0, 2, 3, 4, 14, 38, 4, 210, 13781, 123456, 1437262, 128374};
    this.image4 = new Image(this.pixels4, this.width4, this.height4);
    this.sim1.add("tennis ball", this.image4);

    assertEquals(this.image4, this.sim1.retrieve("tennis ball"));
  }
}