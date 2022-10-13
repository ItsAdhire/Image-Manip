package model.operations;

import model.Image;

/**
 * This class represents an abstraction for macros that must traverse
 * the pixels in an image in order to create a new image (how it creates
 * a new image depends on what operation is done on it).
 */
public abstract class AbstractMovePixelsOp extends AbstractImageOp {
  /**
   * Creates a new AbstractMovePixelOp object using the given input and output image
   * names.
   * @param in the String name of the input image.
   * @param out the String name of the output image.
   */
  public AbstractMovePixelsOp(String in, String out) {
    super(in, out);
  }

  /**
   * Creates a new AbstractMovePixelsOp object using the given input image name.
   * @param in the String name of the input image.
   */
  public AbstractMovePixelsOp(String in) {
    super(in);
  }

  // Moves through the given image's pixel array, where the x-coordinate values start from
  // xStart and end at xEnd, and where the y-coordinate values start from yStart and end at
  // yEnd. Copies the original image's pixels into the moved pixel array for the new image,
  // and returns this Image object.
  protected Image traverseImage(Image image, int xStart, int xEnd, int yStart, int yEnd) {
    int newWidth = Math.abs(xEnd - xStart) + 1;
    int newHeight = Math.abs(yEnd - yStart) + 1;

    int[] original = image.getPixelArray();
    int[] moved = new int[newWidth * newHeight];

    int xDelta = Integer.signum(xEnd - xStart);
    int yDelta = Integer.signum(yEnd - yStart);

    if (xDelta == 0) {
      xDelta = 1;
    }
    if (yDelta == 0) {
      yDelta = 1;
    }

    int count = 0;
    for (int y = yStart; y != yEnd + yDelta; y += yDelta) {
      for (int x = xStart; x != xEnd + xDelta; x += xDelta) {
        moved[count] = original[y * image.getWidth() + x];
        count += 1;
      }
    }
    return new Image(moved, newWidth, newHeight);
  }
}
