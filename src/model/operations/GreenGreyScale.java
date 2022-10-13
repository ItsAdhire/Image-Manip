package model.operations;

/**
 * Represents a macro that grey scales an image based on its green component.
 */
public class GreenGreyScale extends AbstractGreyScaleOp {

  /**
   * Creates the green component greyscale macro.
   *
   * @param in  the name of the input image.
   * @param out the name of the image the changed verseion will output to.
   */
  public GreenGreyScale(String in, String out) {
    super(in, out);
  }

  /**
   * Creates the green component greyscale macro.
   * The image will be replaced by its changed version.
   *
   * @param in name of input image.
   */
  public GreenGreyScale(String in) {
    super(in);
  }

  @Override
  protected int greyScaleValue(int[] color) {
    return color[1];
  }
}
