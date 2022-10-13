package model.operations;

import org.junit.Before;
import org.junit.Test;

import model.Image;
import model.ImageModel;
import model.SimpleImageModel;
import model.operations.Brighten;
import model.operations.ImageOp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents a set of tests for the Brighten class.
 */
public class BrightenTest {

  Image single;
  Image wide;
  Image tall;
  Image square;
  ImageModel im0;
  ImageModel im1;
  ImageModel im2;
  ImageModel im3;

  @Before
  public void init() {
    im0 = new SimpleImageModel();
    im1 = new SimpleImageModel();
    im2 = new SimpleImageModel();
    im3 = new SimpleImageModel();

    single = new Image(new int[]{0xFF00FF}, 1, 1);
    wide = new Image(new int[]{0x000000, 0xFFFFFFFF, 0x012345}, 3, 1);
    tall = new Image(new int[]{0x000000, 0xFFFFFFFF, 0x012345}, 1, 3);
    square = new Image(new int[]{0x010101, 0x020202, 0x333333, 0xFFFF00}, 2, 2);

    im0.add("single", single);
    im1.add("wide", wide);
    im2.add("tall", tall);
    im3.add("square", square);
    im3.add("single", single);
  }

  @Test
  public void testInvalidConstructors() {
    try {
      ImageOp i = new Brighten("test", null, 0);
      fail("Succeeded on null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null argument", e.getMessage());
    }

    try {
      ImageOp i = new Brighten(null, "anothertest", 0);
      fail("Succeeded on null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null argument", e.getMessage());
    }

    try {
      ImageOp i = new Brighten(null, null, -10);
      fail("Succeeded on null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null argument", e.getMessage());
    }

    try {
      ImageOp i = new Brighten(null, 13);
      fail("Succeeded on null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null argument", e.getMessage());
    }

  }


  @Test
  public void testOperation() {
    new Brighten("single", 0).execute(im0);
    new Brighten("wide", "wide-changed", -9).execute(im1);
    new Brighten("tall", "tall-changed", 1).execute(im2);
    new Brighten("square", 7).execute(im3);
    new Brighten("single", "single-changed", -2).execute(im3);

    int[] out0 = new int[]{0xFF00FF};
    int[] out1 = new int[]{0x000000, 0xFFF6F6F6, 0x001A3C};
    int[] out2 = new int[]{0x010101, 0xFFFFFFFF, 0x022446};
    int[] out3 = new int[]{0x080808, 0x090909, 0x3A3A3A, 0xFFFF07};
    int[] out4 = new int[]{0xFD00FD};

    assertArrayEquals(out0, im0.retrieve("single").getPixelArray());
    assertEquals(1, im0.retrieve("single").getWidth());
    assertEquals(1, im0.retrieve("single").getHeight());

    assertArrayEquals(wide.getPixelArray(), im1.retrieve("wide").getPixelArray());
    assertEquals(3, im1.retrieve("wide").getWidth());
    assertEquals(1, im1.retrieve("wide").getHeight());

    assertArrayEquals(out1, im1.retrieve("wide-changed").getPixelArray());
    assertEquals(3, im1.retrieve("wide-changed").getWidth());
    assertEquals(1, im1.retrieve("wide-changed").getHeight());

    assertArrayEquals(tall.getPixelArray(), im2.retrieve("tall").getPixelArray());
    assertEquals(1, im2.retrieve("tall").getWidth());
    assertEquals(3, im2.retrieve("tall").getHeight());

    assertArrayEquals(out2, im2.retrieve("tall-changed").getPixelArray());
    assertEquals(1, im2.retrieve("tall-changed").getWidth());
    assertEquals(3, im2.retrieve("tall-changed").getHeight());

    assertArrayEquals(out3, im3.retrieve("square").getPixelArray());
    assertEquals(2, im3.retrieve("square").getWidth());
    assertEquals(2, im3.retrieve("square").getHeight());

    assertArrayEquals(single.getPixelArray(), im3.retrieve("single").getPixelArray());
    assertEquals(1, im3.retrieve("single").getWidth());
    assertEquals(1, im3.retrieve("single").getHeight());

    assertArrayEquals(out4, im3.retrieve("single-changed").getPixelArray());
    assertEquals(1, im3.retrieve("single-changed").getWidth());
    assertEquals(1, im3.retrieve("single-changed").getHeight());

  }

  @Test
  public void testNoImageFound() {
    try {
      new Brighten("single", -100).execute(im1);
      fail("Succeeded when no image with that name");
    } catch (IllegalStateException e) {
      assertEquals("No image with name single exists", e.getMessage());
    }

    try {
      new Brighten("not found", "doesnt matter", -100).execute(im2);
      fail("Succeeded when no image with that name");
    } catch (IllegalStateException e) {
      assertEquals("No image with name not found exists", e.getMessage());
    }

    try {
      new Brighten("single-changed", "single-changed", -100).execute(im3);
      fail("Succeeded when no image with that name");
    } catch (IllegalStateException e) {
      assertEquals("No image with name single-changed exists", e.getMessage());
    }

    try {
      new Brighten("abcdefghijklmop", -100).execute(im0);
      fail("Succeeded when no image with that name");
    } catch (IllegalStateException e) {
      assertEquals("No image with name abcdefghijklmop exists", e.getMessage());
    }
  }

}
