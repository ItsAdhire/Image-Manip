package model.operations;

/**
 * Represents a macro that grey scales an image based on its red component.
 */
public class RedGreyScale extends AbstractGreyScaleOp {

  /**
   * Creates the red component greyscale macro.
   *
   * @param in  the name of the input image.
   * @param out the name of the image the changed verseion will output to.
   */
  public RedGreyScale(String in, String out) {
    super(in, out);
  }

  /**
   * Creates the red component greyscale macro.
   * The image will be replaced by its changed version.
   *
   * @param in name of input image.
   */
  public RedGreyScale(String in) {
    super(in);
  }

  @Override
  protected int greyScaleValue(int[] color) {
    return color[0];
  }
}
