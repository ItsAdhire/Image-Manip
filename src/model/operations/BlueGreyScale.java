package model.operations;

/**
 * Represents a macro that grey scales an image based on its blue component.
 */
public class BlueGreyScale extends AbstractGreyScaleOp {

  /**
   * Creates the blue component greyscale macro.
   *
   * @param in  the name of the input image.
   * @param out the name of the image the changed verseion will output to.
   */
  public BlueGreyScale(String in, String out) {
    super(in, out);
  }

  /**
   * Creates the blue component greyscale macro.
   * The image will be replaced by its changed version.
   *
   * @param in name of input image.
   */
  public BlueGreyScale(String in) {
    super(in);
  }

  @Override
  protected int greyScaleValue(int[] color) {
    return color[2];
  }
}
