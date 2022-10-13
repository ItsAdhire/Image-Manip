package model.operations;

import model.Image;
import model.PixelUtils;

/**
 * Represents an abstraction for macros that perform the same operation on every pixel
 * in a particular image in the model.
 */
public abstract class AbstractUnitImageOp extends AbstractImageOp {

  /**
   * Sets the input and output names of the images to retrieve and add.
   *
   * @param in  name of the input image the macro will read from.
   * @param out the name of the image the changed version will output to.
   */
  public AbstractUnitImageOp(String in, String out) {
    super(in, out);
  }

  /**
   * Sets the input and output names of the images to be the same.
   * This means that the changed version will overwrite the previous image.
   *
   * @param in the name of the input image the macro will read from.
   */
  public AbstractUnitImageOp(String in) {
    super(in);
  }

  @Override
  protected Image modifyImage(Image image) {
    int[] pixels = image.getPixelArray();
    for (int i = 0; i < pixels.length; i++) {
      int[] color = PixelUtils.toRGBA(pixels[i]);
      int[] newColor = modifyPixel(color);

      pixels[i] = PixelUtils.compactRGBA(newColor);
    }

    return new Image(pixels, image.getWidth(), image.getHeight());
  }

  /**
   * Uses the color of the pixel to produce new colors to represent a pixel.
   *
   * @param color the RGBA values of a pixel
   * @return the RGBA values of the altered version of the pixel.
   */
  protected abstract int[] modifyPixel(int[] color);

}
