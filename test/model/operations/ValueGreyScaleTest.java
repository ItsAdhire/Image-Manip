package model.operations;

import org.junit.Before;
import org.junit.Test;

import model.Image;
import model.ImageModel;
import model.SimpleImageModel;
import model.operations.ImageOp;
import model.operations.ValueGreyScale;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents a set of tests for the ValueGreyScale class.
 */
public class ValueGreyScaleTest {

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
      ImageOp i = new ValueGreyScale("test", null);
      fail("Succeeded on null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null argument", e.getMessage());
    }

    try {
      ImageOp i = new ValueGreyScale(null, "anothertest");
      fail("Succeeded on null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null argument", e.getMessage());
    }

    try {
      ImageOp i = new ValueGreyScale(null, null);
      fail("Succeeded on null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null argument", e.getMessage());
    }

    try {
      ImageOp i = new ValueGreyScale(null);
      fail("Succeeded on null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null argument", e.getMessage());
    }

  }


  @Test
  public void testOperation() {
    new ValueGreyScale("single").execute(im0);
    new ValueGreyScale("wide", "wide-changed").execute(im1);
    new ValueGreyScale("tall", "tall-changed").execute(im2);
    new ValueGreyScale("square").execute(im3);
    new ValueGreyScale("single", "single-changed").execute(im3);

    int[] out0 = new int[]{0xFFFFFF};
    int[] out1 = new int[]{0x000000, 0xFFFFFFFF, 0x454545};
    int[] out2 = new int[]{0x000000, 0xFFFFFFFF, 0x454545};
    int[] out3 = new int[]{0x010101, 0x020202, 0x333333, 0xFFFFFF};

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

    assertArrayEquals(out0, im3.retrieve("single-changed").getPixelArray());
    assertEquals(1, im3.retrieve("single-changed").getWidth());
    assertEquals(1, im3.retrieve("single-changed").getHeight());

  }

  @Test
  public void testNoImageFound() {
    try {
      new ValueGreyScale("single").execute(im1);
      fail("Succeeded when no image with that name");
    } catch (IllegalStateException e) {
      assertEquals("No image with name single exists", e.getMessage());
    }

    try {
      new ValueGreyScale("not found", "doesnt matter").execute(im2);
      fail("Succeeded when no image with that name");
    } catch (IllegalStateException e) {
      assertEquals("No image with name not found exists", e.getMessage());
    }

    try {
      new ValueGreyScale("single-changed", "single-changed").execute(im3);
      fail("Succeeded when no image with that name");
    } catch (IllegalStateException e) {
      assertEquals("No image with name single-changed exists", e.getMessage());
    }

    try {
      new ValueGreyScale("abcdefghijklmop").execute(im0);
      fail("Succeeded when no image with that name");
    } catch (IllegalStateException e) {
      assertEquals("No image with name abcdefghijklmop exists", e.getMessage());
    }
  }

}
