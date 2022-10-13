package model.operations;

/**
 * Represents a macro that grey scales an image based on its luma values
 * (the weighted sum of the colors of a pixel).
 */
public class LumaGreyScale extends AbstractGreyScaleOp {

  /**
   * Creates the luma value greyscale macro.
   *
   * @param in  the name of the input image.
   * @param out the name of the image the changed verseion will output to.
   */
  public LumaGreyScale(String in, String out) {
    super(in, out);
  }


  /**
   * Creates the luma value greyscale macro.
   * The image will be replaced by its changed version.
   *
   * @param in name of input image.
   */
  public LumaGreyScale(String in) {
    super(in);
  }

  @Override
  protected int greyScaleValue(int[] color) {
    return (int) Math.round(0.2126 * color[0] + 0.7152 * color[1] + 0.0722 * color[2]);
  }
}
