package model;

/**
 * This class represents a set of operations that can be used to
 * access the features of an image.
 */
public class Image {
  // INVARIANTS: the width and height are always positive and
  // their product is always the pixel array's length
  private final int width;
  private final int height;

  private final int[] pixels;

  /**
   * Creates a new Image object using the given pixels
   * array, width, and height.
   * @param pixels an integer array representing the bit color values
   *      of all the pixels in an image.
   * @param width an integer representing the width of an image.
   * @param height an integer representing the height of an image.
   */
  public Image(int[] pixels, int width, int height) {
    if (pixels == null) {
      throw new IllegalArgumentException("Given null pixel array");
    }
    if (width < 1 || height < 1 || pixels.length != width * height) {
      throw new IllegalArgumentException("Given invalid size dimensions");
    }

    this.width = width;
    this.height = height;
    this.pixels = copyPixels(pixels);
  }

  /**
   * Returns this Image object's width value.
   * @return an integer representing the width.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns this Image object's height value.
   * @return an integer representing the height.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns this Image object's pixel array.
   * @return an integer array representing all the pixels'
   *      bit color values in this image.
   */
  public int[] getPixelArray() {
    return copyPixels(pixels);
  }

  // Provides a deep copy of this Image object's pixel array
  // so that the user will not be able to mutate it
  private int[] copyPixels(int[] pixels) {
    int[] out = new int[pixels.length];
    for (int i = 0; i < out.length; i++) {
      out[i] = pixels[i];
    }
    return out;
  }


}
