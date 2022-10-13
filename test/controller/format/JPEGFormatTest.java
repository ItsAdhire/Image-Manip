package controller.format;

import org.junit.Test;

import java.io.IOException;

import model.Image;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents a set of tests for the JPEGFormat class.
 */
public class JPEGFormatTest {

  @Test
  public void testValidLoad() {
    Image i1 = new JPEGFormat().load("res/random1.jpeg");
    Image i2 = new JPEGFormat().load("res/random2.jpg");

    int[] pix1 = new int[]{
      -61, -4417, -3898236, -13564650, -16373710, -8486833, -6386317,
      -5080694, -8957324, -8675650, -16776704, -13950676, -9287811, -9217630, -8478486
    };

    int[] pix2 = new int[]{-1310721, -12560812, -12758191, -6639442};

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
      Image i1 = new JPEGFormat().load("res/doesnt_exist");
      fail("fail when no file exists");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid File Path", e.getMessage());

    }

    try {
      Image i1 = new JPEGFormat().load("invalid path string");
      fail("fail when no file exists");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid File Path", e.getMessage());

    }

    try {
      Image i1 = new JPEGFormat().load("res/random99.jpeg");
      fail("fail when no file exists");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid File Path", e.getMessage());
    }

    try {
      Image i1 = new JPEGFormat().load("test/controller/format/PPMFormatTest.java");
      fail("File is not of type JPEG");
    } catch (IllegalArgumentException e) {
      assertEquals("File type is not properly formatted", e.getMessage());
    }

    try {
      Image i1 = new JPEGFormat().load("res/random1.ppm");
      fail("Succeeded on loading incorrectly formatted ppm");
    } catch (IllegalArgumentException e) {
      assertEquals("File type is not properly formatted", e.getMessage());
    }

  }

  @Test
  public void testSave() throws IOException {
    int[] pix1 = new int[]{
      -61, -4417, -3898236, -13564650, -16373710, -8486833, -6386317,
      -5080694, -8957324, -8675650, -16776704, -13950676, -9287811, -9217630, -8478486
    };

    int[] pix2 = new int[]{-1310721, -12560812, -12758191, -6639442};

    Image i1 = new PPMFormat().load("res/random1.ppm");
    new JPEGFormat().save("res/random2-copy.jpeg", i1);
    Image saved = new JPEGFormat().load("res/random2-copy.jpeg");

    assertEquals(5, saved.getWidth());
    assertEquals(3, saved.getHeight());
    assertArrayEquals(pix1, saved.getPixelArray());

    // Test overwrite
    Image i2 = new PPMFormat().load("res/random2.ppm");
    new JPEGFormat().save("res/random2-copy.jpeg", i2);
    saved = new JPEGFormat().load("res/random2-copy.jpeg");

    assertEquals(2, saved.getWidth());
    assertEquals(2, saved.getHeight());
    assertArrayEquals(pix2, saved.getPixelArray());

    try {
      new JPEGFormat().save("fakedir/fake.png", i2);
      fail("saved to invalid path");
    } catch (IOException e) {
      assertEquals("Given invalid path to file", e.getMessage());
    }

    try {
      new JPEGFormat().save("here", null);
      fail("saved null image to path");
    } catch (IllegalArgumentException e) {
      assertEquals("Inputs cannot be null", e.getMessage());
    }
  }


}
