package model.operations;

import org.junit.Before;
import org.junit.Test;

import model.Image;
import model.ImageModel;
import model.SimpleImageModel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents a set of tests for the Sepia class.
 */
public class SepiaTest {
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
  public void testInvalidInitializationConstructor1() {
    try {
      ImageOp op1 = new Sepia(null, "Northeastern");
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      if (!(e.getMessage().equals("Given a null argument"))) {
        fail("Did not throw correct IllegalArgumentException");
      }
    }

    try {
      ImageOp op2 = new Sepia("Husky", null);
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      if (!(e.getMessage().equals("Given a null argument"))) {
        fail("Did not throw correct IllegalArgumentException");
      }
    }

    try {
      ImageOp op3 = new Sepia(null, null);
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      if (!(e.getMessage().equals("Given a null argument"))) {
        fail("Did not throw correct IllegalArgumentException");
      }
    }
  }

  @Test
  public void testInvalidInitializationConstructor2() {
    try {
      ImageOp op1 = new Sepia(null);
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      if (!(e.getMessage().equals("Given a null argument"))) {
        fail("Did not throw correct IllegalArgumentException");
      }
    }
  }

  @Test
  public void testValidExecuteImageModel0() {
    ImageOp op1 = new Sepia("single");
    op1.execute(im0);

    int[] out0 = new int[]{0x948467};

    Image image1 = im0.retrieve("single");
    assertArrayEquals(out0, image1.getPixelArray());
    assertEquals(1, image1.getWidth());
    assertEquals(1, image1.getHeight());
  }

  @Test
  public void testValidExecuteImageModel1() {
    ImageOp op1 = new Sepia("wide", "wide-changed");
    op1.execute(im1);

    int[] out0 = new int[]{0x000000, 0xFFFFFFFF, 0x012345};
    int[] out1 = new int[]{0x000000, 0xFFFFFFEF, 0x28241C};

    Image image1 = im1.retrieve("wide");
    assertArrayEquals(out0, image1.getPixelArray());
    assertEquals(3, image1.getWidth());
    assertEquals(1, image1.getHeight());

    Image image2 = im1.retrieve("wide-changed");
    assertArrayEquals(out1, image2.getPixelArray());
    assertEquals(3, image2.getWidth());
    assertEquals(1, image2.getHeight());
  }

  @Test
  public void testValidExecuteImageModel2() {
    ImageOp op1 = new Sepia("tall", "tall-changed");
    op1.execute(im2);

    int[] out0 = new int[]{0x000000, 0xFFFFFFFF, 0x012345};
    int[] out1 = new int[]{0x000000, 0xFFFFFFEF, 0x28241C};

    Image image1 = im2.retrieve("tall");
    assertArrayEquals(out0, image1.getPixelArray());
    assertEquals(1, image1.getWidth());
    assertEquals(3, image1.getHeight());

    Image image2 = im2.retrieve("tall-changed");
    assertArrayEquals(out1, image2.getPixelArray());
    assertEquals(1, image2.getWidth());
    assertEquals(3, image2.getHeight());
  }

  @Test
  public void testValidExecuteImageModel3() {
    ImageOp op1 = new Sepia("square", "square-changed");
    op1.execute(im3);

    int[] out0 = new int[]{0x010101, 0x020202, 0x333333, 0xFFFF00};
    int[] out1 = new int[]{0x010101, 0x030202, 0x453D30, 0xFFFFCE};

    Image image1 = im3.retrieve("square");
    assertArrayEquals(out0, image1.getPixelArray());
    assertEquals(2, image1.getWidth());
    assertEquals(2, image1.getHeight());

    Image image2 = im3.retrieve("square-changed");
    assertArrayEquals(out1, image2.getPixelArray());
    assertEquals(2, image2.getWidth());
    assertEquals(2, image2.getHeight());


    ImageOp op2 = new Sepia("single", "square");
    op2.execute(im3);

    int[] out2 = new int[]{0xFF00FF};
    int[] out3 = new int[]{0x948467};

    Image image3 = im3.retrieve("single");
    assertArrayEquals(out2, image3.getPixelArray());
    assertEquals(1, image3.getWidth());
    assertEquals(1, image3.getHeight());

    Image image4 = im3.retrieve("square");
    assertArrayEquals(out3, image4.getPixelArray());
    assertEquals(1, image4.getWidth());
    assertEquals(1, image4.getHeight());
  }

  @Test
  public void testInvalidExecute() {
    ImageOp op1 = new Sepia("Boston");
    try {
      op1.execute(im0);
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      if (!(e.getMessage().equals("No image with name Boston exists"))) {
        fail("Did not throw correct IllegalStateException");
      }
    }

    ImageOp op2 = new Sepia("hello", "goodbye");
    try {
      op2.execute(im1);
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      if (!(e.getMessage().equals("No image with name hello exists"))) {
        fail("Did not throw correct IllegalStateException");
      }
    }

    ImageOp op3 = new Sepia("trapezoid", "trapezoid");
    try {
      op3.execute(im2);
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      if (!(e.getMessage().equals("No image with name trapezoid exists"))) {
        fail("Did not throw correct IllegalStateException");
      }
    }

    ImageOp op4 = new Sepia("tall");
    try {
      op4.execute(im3);
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      if (!(e.getMessage().equals("No image with name tall exists"))) {
        fail("Did not throw correct IllegalStateException");
      }
    }
  }
}
