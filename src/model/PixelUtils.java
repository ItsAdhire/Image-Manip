package model;

/**
 * This class represents a set of operations to convert pixels between
 * rgba color format and bit color format.
 */
public class PixelUtils {
  /**
   * Converts a pixel's rgba representation to a bit representation.
   * @param rgba an integer array that holds the r, g, b, and opacity values
   *      of a particular pixel.
   * @return an integer that represents the color value of a particular pixel
   *      in bits.
   */
  public static int compactRGBA(int[] rgba) {
    for (int i = 0; i < rgba.length; i++) {
      rgba[i] = Math.min(255, Math.max(0, rgba[i]));
    }
    return (rgba[3] << 24) | (rgba[0] << 16) | (rgba[1] << 8) | rgba[2];
  }

  /**
   * Converts a pixel's bit representation to a rgba representation.
   * @param i an integer that represents the color value of a particular pixel
   *      int bits.
   * @return an integer array that holds the r, g, b, and opacity values
   *      of a particular pixel.
   */
  public static int[] toRGBA(int i) {
    return new int[]{
        (i >> 16) & 0xFF,
        (i >> 8) & 0xFF,
        i & 0xFF,
        (i >> 24) & 0xFF,
    };
  }
}
