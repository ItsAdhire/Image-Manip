package model.operations;


/**
 * Represents a macro that grey scales an image based on its value values
 * (the maximum color value of a pixel).
 */
public class ValueGreyScale extends AbstractGreyScaleOp {

  /**
   * Creates the value greyscale macro.
   *
   * @param in  the name of the input image.
   * @param out the name of the image the changed verseion will output to.
   */
  public ValueGreyScale(String in, String out) {
    super(in, out);
  }


  /**
   * Creates the value greyscale macro.
   * The image will be replaced by its changed version.
   *
   * @param in name of input image.
   */
  public ValueGreyScale(String in) {
    super(in);
  }

  @Override
  protected int greyScaleValue(int[] color) {
    return Math.max(color[0], Math.max(color[1], color[2]));
  }
}
