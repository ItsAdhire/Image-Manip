package model.operations;

/**
 * Represents a macro that brightens or darkens an image based on
 * a given change in brightness (that can be either positive or negative).
 */
public class Brighten extends AbstractUnitImageOp {

  private int delta;

  /**
   * Creates the brightening macro.
   *
   * @param in    the name of the input image.
   * @param out   the name of the image the changed version will output to.
   * @param delta the change in brightness (where negative means darkening).
   */
  public Brighten(String in, String out, int delta) {
    super(in, out);
    this.delta = delta;
  }


  /**
   * Creates the brightening macro.
   * The image will be replaced with it's changed version.
   *
   * @param in    the name of the input image.
   * @param delta the change in brightness (where negative means darkening).
   */
  public Brighten(String in, int delta) {
    super(in);
    this.delta = delta;
  }

  @Override
  protected int[] modifyPixel(int[] color) {
    for (int i = 0; i < 3; i++) {
      color[i] = color[i] + delta;
    }

    return color;
  }
}
