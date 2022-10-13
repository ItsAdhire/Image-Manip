package model.operations;

/**
 * Represents an abstraction for macros that perform a greyscale on every pixel
 * of a particular image in the model.
 */
public abstract class AbstractGreyScaleOp extends AbstractUnitImageOp {

  /**
   * Sets the input and output names of the images to retrieve and add.
   *
   * @param in  name of the input image the macro will read from.
   * @param out the name of the image the changed version will output to.
   */
  public AbstractGreyScaleOp(String in, String out) {
    super(in, out);
  }


  /**
   * Sets the input and output names of the images to be the same.
   * This means that the changed version will overwrite the previous image.
   *
   * @param in the name of the input image the macro will read from.
   */
  public AbstractGreyScaleOp(String in) {
    super(in);
  }

  @Override
  protected int[] modifyPixel(int[] color) {
    int val = greyScaleValue(color);
    for (int i = 0; i < 3; i++) {
      color[i] = val;
    }
    return color;
  }

  /**
   * Uses the color of the pixel to produce the value that all the colors of a
   * pixel should be set to.
   *
   * @param color the RGBA values of a pixel
   * @return the RGBA values of the altered version of the pixel.
   */
  protected abstract int greyScaleValue(int[] color);


}
