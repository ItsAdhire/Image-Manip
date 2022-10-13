package controller.format;

import org.junit.Test;

import java.io.IOException;

import model.Image;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents a set of tests for the PNGFormat class.
 */
public class PNGFormatTest {

  @Test
  public void testValidLoad() {
    Image i1 = new PNGFormat().load("res/random1.png");
    Image i2 = new PNGFormat().load("res/random2.png");

    int[] pix1 = new int[]{
      -1, -129, -33024, -8454144, -16777089, -8421505, -6255456,
      -49921, -3670008, -13766556, -16777216, -15456216, -13477226, -11498296, -10184961
    };

    int[] pix2 = new int[]{-1, -8113396, -8709994, -15794233};

    assertEquals(5, i1.getWidth());
    assertEquals(3, i1.getHeight());
    assertArrayEquals(pix1, i1.getPixelArray());

    assertEquals(2, i2.getWidth());
    assertEquals(2, i2.getHeight());
    assertArrayEquals(pix2, i2.getPixelArray());
  }

  @Test
  public void testInvalidLoad() {

    try {
      Image i1 = new PNGFormat().load("res/doesnt_exist");
      fail("fail when no file exists");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid File Path", e.getMessage());

    }

    try {
      Image i1 = new PNGFormat().load("invalid path string");
      fail("fail when no file exists");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid File Path", e.getMessage());

    }

    try {
      Image i1 = new PNGFormat().load("res/random99.ppm");
      fail("fail when no file exists");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid File Path", e.getMessage());
    }

    try {
      Image i1 = new PNGFormat().load("test/controller/format/PPMFormatTest.java");
      fail("File is not of type PNG");
    } catch (IllegalArgumentException e) {
      assertEquals("File type is not properly formatted", e.getMessage());
    }

    try {
      Image i1 = new PNGFormat().load("res/corrupt0.ppm");
      fail("Succeeded on loading incorrectly formatted ppm");
    } catch (IllegalArgumentException e) {
      assertEquals("File type is not properly formatted", e.getMessage());
    }

    try {
      Image i1 = new PNGFormat().load("res/corrupt1.ppm");
      fail("Succeeded on loading incorrectly formatted ppm");
    } catch (IllegalArgumentException e) {
      assertEquals("File type is not properly formatted", e.getMessage());
    }
  }

  @Test
  public void testSave() throws IOException {

    int[] pix1 = new int[]{
      -1, -129, -33024, -8454144, -16777089, -8421505, -6255456,
      -49921, -3670008, -13766556, -16777216, -15456216, -13477226, -11498296, -10184961
    };

    int[] pix2 = new int[]{-1, -8113396, -8709994, -15794233};

    Image i1 = new PPMFormat().load("res/random1.ppm");
    new PNGFormat().save("res/random2-copy.png", i1);
    Image saved = new PNGFormat().load("res/random2-copy.png");

    assertEquals(5, saved.getWidth());
    assertEquals(3, saved.getHeight());
    assertArrayEquals(pix1, saved.getPixelArray());

    // Test overwrite
    Image i2 = new PPMFormat().load("res/random2.ppm");
    new PNGFormat().save("res/random2-copy.png", i2);
    saved = new PNGFormat().load("res/random2-copy.png");

    assertEquals(2, saved.getWidth());
    assertEquals(2, saved.getHeight());
    assertArrayEquals(pix2, saved.getPixelArray());

    try {
      new PNGFormat().save("fakedir/fake.png", i2);
      fail("saved to invalid path");
    } catch (IOException e) {
      assertEquals("Given invalid path to file", e.getMessage());
    }

    try {
      new PNGFormat().save("here", null);
      fail("saved null image to path");
    } catch (IllegalArgumentException e) {
      assertEquals("Inputs cannot be null", e.getMessage());
    }
  }

}
