package model.operations;

import model.Image;
import model.PixelUtils;

/**
 * Represents an abstraction for macros that perform a filter operation on an image.
 */
public abstract class AbstractFilter extends AbstractImageOp {
  protected double[][] matrix;

  /**
   * Creates a new AbstractFilter object using the given in and out image names.
   * @param in the String name of the input image.
   * @param out the String name of the output image.
   */
  public AbstractFilter(String in, String out) {
    super(in, out);
    this.matrix = this.initializeMatrix();
  }

  /**
   * Creates a new AbstractFilter object using the given in image name.
   * @param in the String name of the input image.
   */
  public AbstractFilter(String in) {
    this(in, in);
  }

  // Creates a matrix with specific dimensions and values depending on the type of
  // filter operation
  protected abstract double[][] initializeMatrix();

  @Override
  protected Image modifyImage(Image image) {
    int width = image.getWidth();
    int height = image.getHeight();
    int[] pixels = image.getPixelArray();
    int[] filteredPixels = new int[width * height];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int[] color = PixelUtils.toRGBA(pixels[y * width + x]);
        int[] newColor = this.applyKernel(x, y, width, height, pixels);
        for (int i = 0; i < 3; i++) {
          color[i] = newColor[i];
        }
        filteredPixels[y * width + x] = PixelUtils.compactRGBA(color);
      }
    }

    return new Image(filteredPixels, width, height);
  }

  // Returns an integer array representing the red, green, and blue values of a pixel
  // in the filtered image. Calculates these values by multiplying the kernel values
  // by the corresponding color values of the pixel in the original image and adding
  // these products together
  private int[] applyKernel(int x, int y, int width, int height, int[] pixels) {
    int deltaY = matrix.length / 2;
    int deltaX = matrix[0].length / 2;
    int xCounter = 0;
    int yCounter = 0;
    double rSum = 0;
    double gSum = 0;
    double bSum = 0;

    for (int h = y - deltaY; h <= y + deltaY; h++) {
      for (int w = x - deltaX; w <= x + deltaX; w++) {
        int[] colors = PixelUtils.toRGBA(this.getImageValue(w, h, width, height, pixels));
        double kernelValue = matrix[yCounter][xCounter];
        rSum += (kernelValue * colors[0]);
        gSum += (kernelValue * colors[1]);
        bSum += (kernelValue * colors[2]);
        xCounter += 1;
      }
      xCounter = 0;
      yCounter += 1;
    }

    return new int[]{(int) Math.round(rSum), (int) Math.round(gSum), (int) Math.round(bSum)};
  }

  // Returns 0 if the given indexes represent a position that is out of bounds (in relation
  // to the pixels array). Otherwise, returns the bit color value in the pixels array
  private int getImageValue(int x, int y, int width, int height, int[] pixels) {
    if (x < 0 || y < 0 || x >= width || y >= height) {
      return 0;
    }
    return pixels[y * width + x];
  }
}
