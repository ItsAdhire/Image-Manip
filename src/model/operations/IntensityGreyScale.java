package model.operations;


/**
 * Represents a macro that grey scales an image based on its intensity values
 * (the average value of the colors of a pixel).
 */
public class IntensityGreyScale extends AbstractGreyScaleOp {

  /**
   * Creates the intensity value greyscale macro.
   *
   * @param in  the name of the input image.
   * @param out the name of the image the changed verseion will output to.
   */
  public IntensityGreyScale(String in, String out) {
    super(in, out);
  }

  /**
   * Creates the intensity value greyscale macro.
   * The image will be replaced by its changed version.
   *
   * @param in name of input image.
   */
  public IntensityGreyScale(String in) {
    super(in);
  }

  @Override
  protected int greyScaleValue(int[] color) {
    return (color[0] + color[1] + color[2]) / 3;
  }
}
