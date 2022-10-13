package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

/**
 * Represents a set of tests for the Image class.
 */
public class ImageTest {
  Image image1;
  Image image2;
  Image image3;
  Image image4;
  int width1;
  int width2;
  int width3;
  int width4;
  int height1;
  int height2;
  int height3;
  int height4;
  int[] pixels1;
  int[] pixels2;
  int[] pixels3;
  int[] pixels4;

  // Tests the constructor in the Image class
  @Test
  public void testValidInitializationConstructor() {
    this.width1 = 1;
    this.height1 = 1;
    this.pixels1 = new int[]{20};
    this.image1 = new Image(this.pixels1, this.width1, this.height1);
    assertEquals(this.image1.getWidth(), this.width1);
    assertEquals(this.image1.getHeight(), this.height1);
    assertArrayEquals(this.image1.getPixelArray(), this.pixels1);

    this.width2 = 4;
    this.height2 = 4;
    this.pixels2 = new int[]{-10000, -7830, -400, -15, -1, -7830, 0, 2,
      3, 4, 14, 38, 4, 210, 13781, 123456};
    this.image2 = new Image(this.pixels2, this.width2, this.height2);
    assertEquals(this.image2.getWidth(), this.width2);
    assertEquals(this.image2.getHeight(), this.height2);
    assertArrayEquals(this.image2.getPixelArray(), this.pixels2);

    this.width3 = 2;
    this.height3 = 3;
    this.pixels3 = new int[]{-34500, -15, 5, 27, 176, 210};
    this.image3 = new Image(this.pixels3, this.width3, this.height3);
    assertEquals(this.image3.getWidth(), this.width3);
    assertEquals(this.image3.getHeight(), this.height3);
    assertArrayEquals(this.image3.getPixelArray(), this.pixels3);

    this.width4 = 5;
    this.height4 = 4;
    this.pixels4 = new int[]{-2459060, -32949, -10000, -7830, -400, -15,
      -1, -7830, 0, 2, 3, 4, 14, 38, 4, 210, 13781, 123456, 1437262, 128374};
    this.image4 = new Image(this.pixels4, this.width4, this.height4);
    assertEquals(this.image4.getWidth(), this.width4);
    assertEquals(this.image4.getHeight(), this.height4);
    assertArrayEquals(this.image4.getPixelArray(), this.pixels4);
  }

  // Tests for an IllegalArgumentException thrown by the constructor
  // in the Image class
  @Test
  public void testInvalidInitializationConstructor() {
    this.width1 = 4;
    this.height1 = 4;
    this.pixels1 = null;
    try {
      this.image1 = new Image(this.pixels1, this.width1, this.height1);
      fail("Did not throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given null pixel array", e.getMessage());
    }

    this.width2 = 0;
    this.height2 = 1;
    this.pixels2 = new int[]{-10000, -7830, -400, -15, -1, -7830, 0, 2,
      3, 4, 14, 38, 4, 210, 13781, 123456};
    try {
      this.image2 = new Image(this.pixels2, this.width2, this.height2);
      fail("Did not throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given invalid size dimensions", e.getMessage());
    }

    this.width3 = 3;
    this.height3 = -1;
    this.pixels3 = new int[]{5, 15, 27, 152, 176, 210};
    try {
      this.image3 = new Image(this.pixels3, this.width3, this.height3);
      fail("Did not throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given invalid size dimensions", e.getMessage());
    }

    this.width4 = 3;
    this.height4 = 2;
    this.pixels4 = new int[]{0, 1, 2, 3, 4};
    try {
      this.image4 = new Image(this.pixels4, this.width4, this.height4);
      fail("Did not throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given invalid size dimensions", e.getMessage());
    }
  }

  // Tests the getWidth method in the Image class
  @Test
  public void testGetWidth() {
    this.width1 = 1;
    this.height1 = 1;
    this.pixels1 = new int[]{20};
    this.image1 = new Image(this.pixels1, this.width1, this.height1);
    assertEquals(this.width1, this.image1.getWidth());

    this.width2 = 4;
    this.height2 = 4;
    this.pixels2 = new int[]{-10000, -7830, -400, -15, -1, -7830, 0, 2,
      3, 4, 14, 38, 4, 210, 13781, 123456};
    this.image2 = new Image(this.pixels2, this.width2, this.height2);
    assertEquals(this.width2, this.image2.getWidth());

    this.width3 = 2;
    this.height3 = 3;
    this.pixels3 = new int[]{-34500, -15, 5, 27, 176, 210};
    this.image3 = new Image(this.pixels3, this.width3, this.height3);
    assertEquals(this.width3, this.image3.getWidth());

    this.width4 = 5;
    this.height4 = 4;
    this.pixels4 = new int[]{-2459060, -32949, -10000, -7830, -400, -15,
      -1, -7830, 0, 2, 3, 4, 14, 38, 4, 210, 13781, 123456, 1437262, 128374};
    this.image4 = new Image(this.pixels4, this.width4, this.height4);
    assertEquals(this.width4, this.image4.getWidth());
  }

  // Tests the getHeight method in the Image class
  @Test
  public void testGetHeight() {
    this.width1 = 1;
    this.height1 = 1;
    this.pixels1 = new int[]{20};
    this.image1 = new Image(this.pixels1, this.width1, this.height1);
    assertEquals(this.height1, this.image1.getHeight());

    this.width2 = 4;
    this.height2 = 4;
    this.pixels2 = new int[]{-10000, -7830, -400, -15, -1, -7830, 0, 2,
      3, 4, 14, 38, 4, 210, 13781, 123456};
    this.image2 = new Image(this.pixels2, this.width2, this.height2);
    assertEquals(this.height2, this.image2.getHeight());

    this.width3 = 2;
    this.height3 = 3;
    this.pixels3 = new int[]{-34500, -15, 5, 27, 176, 210};
    this.image3 = new Image(this.pixels3, this.width3, this.height3);
    assertEquals(this.height3, this.image3.getHeight());

    this.width4 = 5;
    this.height4 = 4;
    this.pixels4 = new int[]{-2459060, -32949, -10000, -7830, -400, -15,
      -1, -7830, 0, 2, 3, 4, 14, 38, 4, 210, 13781, 123456, 1437262, 128374};
    this.image4 = new Image(this.pixels4, this.width4, this.height4);
    assertEquals(this.height4, this.image4.getHeight());
  }

  // Tests the getPixelArray method in the Image class
  @Test
  public void testGetPixelArray() {
    this.width1 = 1;
    this.height1 = 1;
    this.pixels1 = new int[]{20};
    this.image1 = new Image(this.pixels1, this.width1, this.height1);
    assertArrayEquals(this.pixels1, this.image1.getPixelArray());

    this.width2 = 4;
    this.height2 = 4;
    this.pixels2 = new int[]{-10000, -7830, -400, -15, -1, -7830, 0, 2,
      3, 4, 14, 38, 4, 210, 13781, 123456};
    this.image2 = new Image(this.pixels2, this.width2, this.height2);
    assertArrayEquals(this.pixels2, this.image2.getPixelArray());

    this.width3 = 2;
    this.height3 = 3;
    this.pixels3 = new int[]{-34500, -15, 5, 27, 176, 210};
    this.image3 = new Image(this.pixels3, this.width3, this.height3);
    assertArrayEquals(this.pixels3, this.image3.getPixelArray());

    this.width4 = 5;
    this.height4 = 4;
    this.pixels4 = new int[]{-2459060, -32949, -10000, -7830, -400, -15,
      -1, -7830, 0, 2, 3, 4, 14, 38, 4, 210, 13781, 123456, 1437262, 128374};
    this.image4 = new Image(this.pixels4, this.width4, this.height4);
    assertArrayEquals(this.pixels4, this.image4.getPixelArray());
  }
}